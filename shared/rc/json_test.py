#!/usr/bin/env python

import json

f = open('models.json', 'r')
models_str = f.read()
f.close()

models_json = json.loads(models_str)

print models_json
