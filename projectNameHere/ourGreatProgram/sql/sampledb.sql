USE PizzaBase;

#--Customer values
INSERT INTO Customer VALUES (NULL, 'Jack', 'Sparrow', 'Harald Gilles veg', '1', 7052 'Trondheim', 56729572, NULL);
INSERT INTO Customer VALUES (NULL, 'The', 'Engineer', 'O. S. Bragstads Plass', '2E', 7034 'Trondheim', 38562047, NULL);
INSERT INTO Customer VALUES (NULL, 'Sherlock', 'Holmes', 'Konrad Dahls Vei', '22', 7024 'Trondheim', 50174506, NULL);
INSERT INTO Customer VALUES (NULL, 'Commander', 'Shepard', 'Aunevegen', '10', 7021 'Trondheim', 92548936, NULL);
INSERT INTO Customer VALUES (NULL, 'Dr.', 'Troll', 'Tryms Veg', '8', 7033 'Trondheim', 83670186, NULL);
INSERT INTO Customer VALUES (NULL, 'The', 'Emperor', 'Ole L. Kolstads veg', '17', 7088 'Trondheim', 20672860, NULL);
INSERT INTO Customer VALUES (NULL, 'Pepper', 'Roni', 'Paul Røstads veg', '9', 7039 'Trondheim', 60257305, NULL);
INSERT INTO Customer VALUES (NULL, 'Steve', 'Jobs', 'Sunnlandsvegen', '16', 7032 'Trondheim', 60125183, NULL);
INSERT INTO Customer VALUES (NULL, 'Wall', 'E', 'Klosterenget', '6', 7030 'Trondheim', 20572919, NULL);
INSERT INTO Customer VALUES (NULL, 'Gordon', 'Freeman', 'Ada Arnfinsens veg', '18b', 7036 'Trondheim', 89353926, NULL);

#--Order values
INSERT INTO Order VALUES (NULL, 3, '2011-09-22 12:44:51', 'registered', 'pickupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 2, '2011-09-23 11:46:33', 'registered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 1, '2011-09-22 8:31:22', 'being cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 7, '2011-09-22 19:36:01', 'being cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 5, '2011-09-23 10:33:29', 'has been cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 4, '2011-09-22 01:22:46', 'has been cooked', 'pickupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 8, '2011-09-22 12:44:10', 'being delivered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 10, '2011-09-23 19:25:27', 'being delivered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 6, '2011-09-22 12:57:32', 'delivered', 'picupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 9, '2011-09-23 07:33:56', 'delivered', 'deliverAtHome', NULL);

#--Dishes
INSERT INTO Dishes VALUES (NULL, 100.00, 'Small Pepperroni pizza', 		'yes', 	'no', 	'yes', 	'no', 	'yes', 1, NULL);
INSERT INTO Dishes VALUES (NULL, 120.00, 'Medium Pepperroni pizza', 	'yes', 	'no', 	'yes', 	'no', 	'yes', 1, NULL);
INSERT INTO Dishes VALUES (NULL, 150.00, 'Large Pepperroni pizza', 		'yes', 	'no', 	'yes', 	'no', 	'yes', 1, NULL);

INSERT INTO Dishes VALUES (NULL, 150.00, 'Small Meat pizza', 			'yes', 	'no', 	'yes', 	'no', 	'no', 2, NULL);
INSERT INTO Dishes VALUES (NULL, 170.00, 'Medium Meat pizza', 			'yes', 	'no', 	'yes', 	'no', 	'no', 2, NULL);
INSERT INTO Dishes VALUES (NULL, 190.00, 'Large Meat pizza', 			'yes', 	'no', 	'yes', 	'no', 	'no', 2, NULL);

INSERT INTO Dishes VALUES (NULL, 70.00, 'Small Kids pizza', 			'no', 	'no', 	'no', 	'yes', 	'no', 3, NULL);
INSERT INTO Dishes VALUES (NULL, 90.00, 'Medium Kids pizza', 			'no', 	'no', 	'no', 	'yes', 	'no', 3, NULL);

INSERT INTO Dishes VALUES (NULL, 130.00, 'Small Extremely hot pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'yes', 4, NULL);
INSERT INTO Dishes VALUES (NULL, 145.00, 'Medium Extremely hot pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'yes', 4, NULL);
INSERT INTO Dishes VALUES (NULL, 160.00, 'Large Extremely hot pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'yes', 4, NULL);

INSERT INTO Dishes VALUES (NULL, 100.00, 'Small Regular pizza', 		'no', 	'no', 	'no', 	'no', 	'no', 5, NULL);
INSERT INTO Dishes VALUES (NULL, 120.00, 'Medium Regular pizza', 		'no', 	'no', 	'no', 	'no', 	'no', 5, NULL);
INSERT INTO Dishes VALUES (NULL, 135.00, 'Large Regular pizza', 		'no', 	'no', 	'no', 	'no', 	'no', 5, NULL);

INSERT INTO Dishes VALUES (NULL, 150.00, 'Small Lots-of-stuff pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'no', 6, NULL);
INSERT INTO Dishes VALUES (NULL, 170.00, 'Medium Lots-of-stuff pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'no', 6, NULL);
INSERT INTO Dishes VALUES (NULL, 190.00, 'Large Lots-of-stuff pizza', 	'yes', 	'yes', 	'yes', 	'no', 	'no', 6, NULL);

#--DishExtras
INSERT INTO DishExtras VALUES (NULL, 1, 10.00, 'some extra pepperroni', 7, NULL);
INSERT INTO DishExtras VALUES (NULL, 2, 10.00, 'some extra pepperroni', 7, NULL);
INSERT INTO DishExtras VALUES (NULL, 3, 10.00, 'some extra pepperroni', 7, NULL);

INSERT INTO DishExtras VALUES (NULL, 15, 0.00, 'Free Cola/Fanta/7-up', 8, 9);
INSERT INTO DishExtras VALUES (NULL, 16, 0.00, 'Free Cola/Fanta/7-up', 8, 9);
INSERT INTO DishExtras VALUES (NULL, 17, 0.00, 'Free Cola/Fanta/7-up', 8, 9);

#--OrderContents
INSERT INTO OrderContents VALUES (1, 3, 1);
INSERT INTO OrderContents VALUES (2, 9, NULL);
INSERT INTO OrderContents VALUES (2, 4, NULL);
INSERT INTO OrderContents VALUES (2, 8, NULL);
INSERT INTO OrderContents VALUES (3, 9, NULL);
INSERT INTO OrderContents VALUES (4, 15, 2);
INSERT INTO OrderContents VALUES (5, 7, NULL);
INSERT INTO OrderContents VALUES (6, 5, NULL);
INSERT INTO OrderContents VALUES (6, 3, NULL);
INSERT INTO OrderContents VALUES (7, 9, NULL);
INSERT INTO OrderContents VALUES (8, 10, NULL);
INSERT INTO OrderContents VALUES (9, 14, NULL);
INSERT INTO OrderContents VALUES (9, 13, NULL);
INSERT INTO OrderContents VALUES (10, 12, NULL);

#--OrderExtrasContent
INSERT INTO OrderExtraContents VALUES (1, 1);
INSERT INTO OrderExtraContents VALUES (2, 4);

#--Config
INSERT INTO Config VALUES('restaurantName', 'The Awesome Pizza Restaurant');
INSERT INTO Config VALUES('deliveryCost', '50.00');
INSERT INTO Config VALUES('freeDeliveryBoundary', '100.00');

#--Comments
INSERT INTO Comments VALUES (NULL, 'A nice, spicy pizza. Can also be used as frisbee.');
INSERT INTO Comments VALUES (NULL, 'It is a pizza. With meat on it.');
INSERT INTO Comments VALUES (NULL, 'See that? It is a toy. For children. And now SHE lives in it!');
INSERT INTO Comments VALUES (NULL, 'We resisted the urge to actually pour some white spirit on it, and light it. It should taste just as hot either way.');
INSERT INTO Comments VALUES (NULL, 'A pizza with some yummy stuff on it');
INSERT INTO Comments VALUES (NULL, 'This one literally barely fits in the oven');

INSERT INTO Comments VALUES (NULL, 'We\'ll put a more generous amount of pepperoni on your pizza');
INSERT INTO Comments VALUES (NULL, 'Buy a lots-of-stuff pizza, and get a free soda bottle!');
INSERT INTO Comments VALUES (NULL, 'Max 1 bottle per pizza');