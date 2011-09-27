#-- == order GUI ==:
#-- retrieve all customers
SELECT * FROM Customer;
#-- retrieve all active orders (ones that have not yet been delivered):
SELECT * FROM Customer LEFT JOIN Orders ON (Customer.CustomerID = Orders.CustomerID) WHERE Orders.OrderStatus != 'delivered' ORDER BY TimeRegistered;
#-- Find a customer by name
SELECT * FROM Customer WHERE firstName='<firstName>' AND lastName='<lastName>' LIMIT 1;
#-- Add a new customer:
INSERT INTO Customer VALUES (NULL, '<firstName>', '<lastName>', '<Address>', '<houseNumber>', '<postCode>', '<town>', '<telephoneNumber>', '<commentID>');
#-- Change user data (replace with appropiate columns):
UPDATE Customer SET firstName='<newName>' WHERE customerID='<customerID>';


#-- == cook GUI ==:
#-- find all uncooked orders:
SELECT * FROM Orders WHERE OrderStatus='registered' ORDER BY TimeRegistered;
#-- mark an order as being cooked
UPDATE Orders SET OrderStatus='being cooked' WHERE OrderID='<OrderID>';





#-- == delivery GUI ==:


#-- == config ==
#-- retrieving a config value:
SELECT * FROM Config WHERE ConfigKey='<keyName>' LIMIT 1
#-- adding a config value:
INSERT INTO Config VALUES ('<configKeyName>', '<configValue>');
#-- modifying a value:
UPDATE Config SET ConfigValue='<newValue>' WHERE ConfigKey='<configKey>'