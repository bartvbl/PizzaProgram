USE PizzaBase;

CREATE TABLE Customer (	CurstomerID INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
FirstName VARCHAR(100) NOT NULL, 
LastName VARCHAR(100) NOT NULL, 
Address VARCHAR(150) NOT NULL,
TelephoneNumber INT NOT NULL,
CommentID INT
);

CREATE TABLE OrderContents ( 
OrderID INT PRIMARY KEY NOT NULL,
DishID INT PRIMARY KEY NOT NULL,
OrderExtrasID INT PRIMARY KEY NOT NULL
);

CREATE TABLE OrderExtrasContent (
OrderExtrasID INT PRIMARY KEY NOT NULL,
DishExtrasID INT PRIMARY KEY NOT NULL
);

CREATE TABLE Order (
OrderID INT PRIMARY KEY NOT NULL,
CustomerID INT PRIMARY KEY NOT NULL,
TimeRegistered DATETIME NOT NULL,
OrderStatus enum ('registered', 'cooked', 'delivered') NOT NULL,
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
DishID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
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