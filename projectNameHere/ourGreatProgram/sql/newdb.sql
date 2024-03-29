USE PizzaBase;

CREATE TABLE Customer (	
CustomerID INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
FirstName VARCHAR(100) NOT NULL, 
LastName VARCHAR(100) NOT NULL, 
Address VARCHAR(150) NOT NULL,
HouseNumber VARCHAR(10) NOT NULL,
PostCode INT NOT NULL,
Town VARCHAR(50) NOT NULL,
TelephoneNumber INT NOT NULL,
CommentID INT
);

CREATE TABLE OrderContents ( 
OrderID INT NOT NULL,
DishID INT NOT NULL,
OrderExtrasID INT
);

CREATE TABLE OrderExtrasContent (
OrderExtrasID INT NOT NULL,
DishExtrasID INT NOT NULL
);

CREATE TABLE Orders (
OrderID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
CustomerID INT NOT NULL,
TimeRegistered DATETIME NOT NULL,
OrderStatus enum ('registered', 'being cooked', 'has been cooked', 'being delivered', 'delivered') NOT NULL,
DeliveryMethod enum ('deliverAtHome', 'pickupAtRestaurant') NOT NULL,
commentID INT
);

CREATE TABLE Dishes (
DishID INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
Price FLOAT NOT NULL,
DishName VARCHAR(200) NOT NULL,
ContainsGluten ENUM('yes', 'no') NOT NULL,
ContainsNuts ENUM('yes', 'no') NOT NULL,
ContainsDairy ENUM('yes', 'no') NOT NULL,
IsVegetarian ENUM('yes', 'no') NOT NULL,
IsSpicy ENUM('yes', 'no') NOT NULL,
DescriptionID INT,
CommentID INT
);

CREATE TABLE DishExtras (
ExtraID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
DishID INT NOT NULL,
Price FLOAT NOT NULL,
ExtraName VARCHAR(100) NOT NULL,
DescriptionID INT,
CommentID INT
);

CREATE TABLE Config (
ConfigKey VARCHAR(30) PRIMARY KEY NOT NULL,
ConfigValue VARCHAR(100) NOT NULL
);

CREATE TABLE Comments (
CommentID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
CommentText TEXT
);