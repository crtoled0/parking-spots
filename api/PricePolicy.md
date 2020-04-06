[Back To Index](./index.md)

# PricePolicy

## **Path: /parking-spots/PricePolicy**  

## Description
Endpoint that works as CRUD maintainer of parking price policies available in the system.  
Every parking spot is assigned to a particular price policy. This way the system will know 
how to calculate price to charge on each parking spot.  
Each policy is composed of following attributes:  
- name: Policy unique identifier name.  
- hourPrice: Price stablished per hour for this policy.  
- fixedAmount: Fixed amount to pay. By default this amount is 0.
- priceFormula = Stablished formula in order to calculate final price to charge.  
The formula is an String with a mathematical operation including the variable fa (fixed amount), hp (hour price) and 
nh (hours that car used parking spot at checkout). Those variable **MUST** be included as part of the formula.   
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

### **Method: GET**
### **Params:**  
- filter (optional): Add keyword to filter returned results. 

### **Request Examples**  

```
GET http://localhost:8080/parking-spots/PricePolicy

GET http://localhost:8080/parking-spots/PricePolicy?filter=kw

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

### **Method: POST**
### **Params:**  
N/A
### **Request Body:**  
 JSON Structure of new policy  

### **Request Example:**  
```
POST http://localhost:8080/parking-spots/PricePolicy
BODY:  {
            "name": "standard-policy",
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

### **Method: PUT**
### **Params:**  
N/A
### **Request Body:**  
JSON Structure of policy to modify

### **Request Example:**  
```
PUT http://localhost:8080/parking-spots/PricePolicy
BODY:  {
            "name": "standard-policy",
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

### **Method: DELETE**
### **Params:**  
- id (mandatory): Identifier name of policy to delete

### **Request Example:**  
```
DELETE http://localhost:8080/parking-spots/PricePolicy?id=policy-test-20kw

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#PricePolicy)
-----------