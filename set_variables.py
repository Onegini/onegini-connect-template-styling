#!/usr/bin/env python3

from os import environ
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
        if (configuration != './config.json'):
            print('File %s was not found. Using default instead' % configuration)
            return loadJson()
        else:
            print('Cannot find default config.json file. Please make sure it is in the same folder as this script')

def loadVars (data, vars='default'):
    print(data[vars])

parsed = loadJson(arguments.config) if arguments.config  else loadJson()

loadVars(parsed)
if arguments.environment: loadVars(parsed, arguments.environment)
