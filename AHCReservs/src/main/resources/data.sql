--inicijalni admin
insert into system_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(0, "sysadmin", "sysadmin", "Anonimus", "Anonimus", "anonimus@gmail.com", 1);

--dummy podaci za hotel admin-a
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "ha1un", "ha1pw", "ha1fn", "ha1ln", "ha1@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "ha2un", "ha2pw", "ha2fn", "ha2ln", "ha2@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "ha3un", "ha3pw", "ha3fn", "ha3ln", "ha3@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ha4un", "ha4pw", "ha4fn", "ha4ln", "ha4@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ha5un", "ha5pw", "ha5fn", "ha5ln", "ha1@gmail.com", 1);

--dummy podaci za airline admin-a
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "aa1un", "aa1pw", "aa1fn", "aa1ln", "aa1@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "aa2un", "aa2pw", "aa2fn", "aa2ln", "aa2@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "aa3un", "aa3pw", "aa3fn", "aa3ln", "aa3@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "aa4un", "aa4pw", "aa4fn", "aa4ln", "aa4@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "aa5un", "aa5pw", "aa5fn", "aa5ln", "aa5@gmail.com", 1);

--dummy podaci za rent a car admin-a
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "ra1un", "ra1pw", "ra1fn", "ra1ln", "ra1@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "ra2un", "ra2pw", "ra2fn", "ra2ln", "ra2@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "ra3un", "ra3pw", "ra3fn", "ra3ln", "ra3@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ra4un", "ra4pw", "ra4fn", "ra4ln", "ra4@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ra5un", "ra5pw", "ra5fn", "ra5ln", "ra5@gmail.com", 1);

insert into registered_user(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "malidzo", "dsa", "Milos", "Malida", "milosmalidza@gmail.com", 1);

insert into rentacar(id, address, description, name, rating) values(0, "dummy address", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Belgrade Rent a car", 0);

insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(0, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Mazda 3 2019", 5, 5, 15, 0, 0);
insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(-1, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Maruti Suzuki Ciaz", 5, 5, 17, 1, 0);
insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(-2, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Datsun GO+", 5, 5, 12, 2, 0);
