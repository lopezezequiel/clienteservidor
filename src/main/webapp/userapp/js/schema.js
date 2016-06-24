$schema = {}

$schema.branch = {
    "type": "object",
    "id": "/api_v1/branch.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "firm": {
          "type": "integer",
          required: true
        },
        "address": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 100
        },
        "latitude": {
            "type": "number",
            "required": true
        },
        "longitude": {
            "type": "number",
            "required": true
        },
        "locality": {
          "type": "integer",
          "required": true
        }
    }
}  

$schema.category = {
	"type": "object",
	"id": "/api_v1/category.schema",
	"properties": {
		"id": {
			"type": "integer",
			"required": false
		},
		"name": {
			"type": "string",
			"required": true,
			"minLength": 2,
			"maxLength": 50
		},
	    "parent": {
	      "type": ["integer", "null"],
	      required: true
	    },
	    "image": {
          "type": ["string", "null"],
          "required": true
	    }
	}
}

$schema.company = {
    "type": "object",
    "id": "/api_v1/company.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "cuit": {
            "type": "string",
            "required": true,
            "minLength": 11,
            "maxLength": 11
        },
        "name": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 30
        }
    }
}

$schema.department = {
    "type": "object",
    "id": "/api_v1/department.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "name": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 100
        },
        "province": {
          "type": "integer",
          "required": true
        }
    }
}

$schema.firm = {
	"type": "object",
	"id": "/api_v1/firm.schema",
	"properties": {
		"id": {
			"type": "integer",
			"required": false
		},
		"name": {
			"type": "string",
			"required": true,
			"minLength": 2,
			"maxLength": 100
		},
	    "company": {
	      "type": "integer",
	      "required": true
	    }
	}
}

$schema.locality = {
    "type": "object",
    "id": "/api_v1/locality.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "name": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 100
        },
        "department": {
          "type": "integer",
          "required": true
        }
    }
}

$schema.product = {
    "type": "object",
    "id": "/api_v1/branch.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "ean13": {
            "type": "string",
            "required": true,
            "minLength": 13,
            "maxLength": 13
        },
        "description": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 100
        },
        "brand": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 50
        },
        "number": {
            "type": "number",
            "required": true
        },
        "unit": {
            "type": "integer",
            "required": true
        },
        "category": {
            "type": "integer",
            "required": true
        }
    }
}

$schema.province = {
    "type": "object",
    "id": "/api_v1/province.schema",
    "properties": {
        "id": {
            "type": "integer",
            "required": false
        },
        "name": {
            "type": "string",
            "required": true,
            "minLength": 2,
            "maxLength": 30
        }
    }
}

$schema.unit = {
    "type": "object",
    "properties": {
        "symbol": {
            "type": "string",
            "required": true,
            "minLength": 1,
            "maxLength": 10
        },
        "singular": {
            "type": "string",
            "required": true,
            "minLength": 1,
            "maxLength": 20
        },
        "plural": {
            "type": "string",
            "required": true,
            "minLength": 1,
            "maxLength": 20
        }
    }
}