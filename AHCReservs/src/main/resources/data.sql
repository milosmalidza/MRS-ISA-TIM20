--inicijalni admin
insert into system_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(0, "sysadmin", "sysadmin", "Anonimus", "Anonimus", "anonimus@gmail.com", 1);

--dummy podaci
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "ha1", "ha1", "ha1", "ha1", "ha1@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "ha2", "ha2", "ha2", "ha2", "ha2@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "ha3", "ha3", "ha3", "ha3", "ha3@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ha4", "ha4", "ha4", "ha4", "ha4@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ha5", "ha5", "ha5", "ha5", "ha1@gmail.com", 1);

insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "aa1", "aa1", "aa1", "aa1", "aa1@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "aa2", "aa2", "aa2", "aa2", "aa2@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "aa3", "aa3", "aa3", "aa3", "aa3@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "aa4", "aa4", "aa4", "aa4", "aa4@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "aa5", "aa5", "aa5", "aa5", "aa5@gmail.com", 1);

insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "ra1", "ra1", "ra1", "ra1", "ra1@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "ra2", "ra2", "ra2", "ra2", "ra2@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "ra3", "ra3", "ra3", "ra3", "ra3@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ra4", "ra4", "ra4", "ra4", "ra4@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ra5", "ra5", "ra5", "ra5", "ra5@gmail.com", 1);

insert into registered_user(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "malidzo", "dsa", "Milos", "Malida", "milosmalidza@gmail.com", 1);

insert into rentacar(id, address, description, name, rating) values(0, "dummy address", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Belgrade Rent a car", 0);

insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(0, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Mazda 3 2019", 5, 5, 15, 0, 0);
insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(-1, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Maruti Suzuki Ciaz", 5, 5, 17, 1, 0);
insert into vehicle(id, archived, description, name, num_of_doors, number_of_seats, price_per_day, vehicle_type, rentacar_id) values(-2, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Datsun GO+", 5, 5, 12, 2, 0);
