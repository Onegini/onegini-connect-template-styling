#!/usr/bin/env python3

import os
import subprocess
import json
import argparse

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


parsed = loadJson(arguments.config) if arguments.config  else loadJson()

loadVars(parsed)
if arguments.environment: loadVars(parsed, arguments.environment)

# npm1 = subprocess.Popen(["npm i"], shell=True, stdout=subprocess.PIPE)
# subprocess.Popen(["xterm"], shell=True, stdin=npm1.stdout)

mvn1 = subprocess.Popen(["mvn spring-boot:run"], shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE)
mvn2 = subprocess.Popen(["open -a terminal"], shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE)
mvn1_out = mvn1.communicate()
mvn2_in = mvn2.communicate(input=mvn1_out[0])