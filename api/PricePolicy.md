[Back To Index](./index.md)

# PricePolicy

## Description
Endpoint that works as CRUD maintainer of parking price policies available in the system.  
Every parking spot is assigned to a particular price policy. This way the system will know 
how to calculate price to charge on each parking spot.  
Each policy is composed of following attributes:  
- name: Policy unique identifier name.  
- hourPrice: Price stablished per hour for this policy.  
- fixedAmount: Fixed amount to pay. By default this amount is 0.
- priceFormula: Stablished formula in order to calculate final price to charge.  
The formula is an string with a mathematical operation including variables 'fa' (fixed amount), 'hp' (hour price) and 
'nh' (hours that car used parking spot at checkout). Those variables **MUST** be included as part of the formula.   
By default the formula to apply is "fa + hp*nh".  

## Scope: Private 

## Services

- [Get Policies](#get-policies).
- [Create Policy](#create-policy).  
- [Edit Polity](#edit-policy).  
- [Delete Policy](#delete-policy).  


-----------

## Get Policies

Endpoint to retrieve policies from the system.

### **Path: /PricePolicy**
### **Method: GET**  
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service
### **Params:**  
- filter (optional): Add keyword to filter returned results. 

### **Request Examples**  

```
GET http://localhost:7005/PricePolicy

GET http://localhost:7005/PricePolicy?filter=kw

```

### **Response Example**

```
{
    "ok": true,
    "items": [
        {
            "name": "policy-20kw",
            "hourPrice": 5.0,
            "fixedAmount": 10.0,
            "priceFormula": "nh > 0.2?(fa + hp*nh):fa"
        },
        {
            "name": "policy-50kw",
            "hourPrice": 5.0,
            "fixedAmount": 15.0,
            "priceFormula": "nh > 0.2?(fa + hp*nh):fa+1"
        }
    ]
}
```

##### [Back To Top](#PricePolicy)
-----------
## Create Policy

Endpoint to create a new policy into the system.

### **Path: /PricePolicy**
### **Method: POST**  
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service
### **Params:**  
N/A
### **Request Body:**  
 JSON Structure of new policy  

### **Request Example:**  
```
POST http://localhost:7005/PricePolicy
BODY:  {
            "hourPrice": 5.0,
            "fixedAmount": 0.0,
            "priceFormula": "nh > 0.1?(fa + hp*nh):0"
        }

```

### **Response Example**  

```
{
    "ok": true
}
```

```
{
    "msg": "E11000 duplicate key error collection: parking-db.PricePolicy index: name_1 dup key: { : \"standard-policy\" }",
    "ok": false
}
```

##### [Back To Top](#PricePolicy)
-----------

## Edit Policy

Endpoint to edit policy from the system.

### **Path: /PricePolicy/{id}**
### **Method: PUT**  
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service
### **Path Variable:**  
- id (mandatory): Policy's id to update. 
### **Request Body:**  
JSON Structure of policy to modify

### **Request Example:**  
```
PUT http://localhost:7005/PricePolicy/standard-policy
BODY:  {
            "hourPrice": 8.0,
            "fixedAmount": 3.0,
            "priceFormula": "nh > 0.1?(fa + hp*nh):0"
        }

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#PricePolicy)
-----------

## Delete Policy

Endpoint to remove policy from the system.

### **Path: /PricePolicy/{id}**
### **Method: DELETE**  
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service.    
### **Path Variable:**  
- id (mandatory): Identifier name of policy to delete

### **Request Example:**  
```
DELETE http://localhost:7005/PricePolicy/policy-test-20kw

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#PricePolicy)
-----------