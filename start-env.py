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
parser.add_argument('-b', '--build', help='Build new JAR for project', action='store_true', required=False)
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
            os.environ[entry] = entries[entry]
    except KeyError:
        print('Cannot find the environment you are looking for in current configuration file.')


def openProcesses(build):
    processes = {}
    if build:
        processes.update({'build': subprocess.Popen("mvn clean package", shell=True)})
    else:
        processes.update({'mvn': subprocess.Popen("mvn spring-boot:run", shell=True)})
        processes.update({'gulp': subprocess.Popen("gulp watcher", shell=True)})
    print(processes)
    return processes


print(arguments)
parsed = loadJson(arguments.config) if arguments.config  else loadJson()

loadVars(parsed)
if arguments.environment: loadVars(parsed, arguments.environment)

pids = openProcesses(arguments.build)

try:
    if 'mvn' in pids:
        pids['mvn'].wait()
    elif 'build' in pids:
        pids['build'].wait()
except KeyboardInterrupt:
    for key, process in pids.items():
        print("Terminating %s" % key)
        process.terminate()
