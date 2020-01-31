#!/usr/bin/env python3

import os
import subprocess
import json
import argparse
import time
import signal

parser = argparse.ArgumentParser(description='Set environment variables for use with springboot, mvn and npm.')
parser.add_argument('-e', '--environment', help='Set the environment to load. Use the name as set in configutation. When omitted uses configured defaults', required=False)
parser.add_argument('-c', '--config', help='Set the configuration file to load. Defaults to config.json', required=False)
arguments = parser.parse_args()

def loadJson( configuration ='./config.json'):
    try:
        with open(configuration) as json_file:
            return json.load(json_file)
    except FileNotFoundError:
        if (configuration == './config.json'):
            print('Cannot find default config.json file. Please make sure it is in the same folder as this script')
        else:
            print('File %s was not found. Using default instead' % configuration)
            return loadJson()

def loadVars (data, vars='default'):
    try:
        entries = data[vars]
        for entry in entries:
            # print(entry, entries[entry])
            os.environ[entry] = entries[entry]
    except KeyError:
        print('Cannot find the environment you are looking for in current configuration file.')

def kill_pid (pids):
    for pid in pids:
        print(pid)
        print(type(pid))
        os.kill(pid, 9)

parsed = loadJson(arguments.config) if arguments.config  else loadJson()

loadVars(parsed)
if arguments.environment: loadVars(parsed, arguments.environment)
gulp = subprocess.Popen(["gulp", "watch"])
mvn = subprocess.Popen(["mvn spring-boot:run"], shell=True)
pids = [ gulp, mvn ]
try:
    mvn.wait()
except KeyboardInterrupt:
    for process in pids:
        print("Terminating %s" % process)
        process.kill()


# signal.getsignal(signal.SIG_DFL), kill_pid(pids))