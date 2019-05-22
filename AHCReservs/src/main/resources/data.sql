--inicijalni admin
insert into system_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(0, "sysadmin", "sysadmin", "Anonimus", "Anonimus", "anonimus@gmail.com", 1);

--dummy podaci za hotel admin-a
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled, hotel_id) values(-1, "ha1un", "ha1pw", "ha1fn", "ha1ln", "ha1@gmail.com", 1, 0);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled, hotel_id) values(-2, "ha2un", "ha2pw", "ha2fn", "ha2ln", "ha2@gmail.com", 1, -1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled, hotel_id) values(-3, "ha3un", "ha3pw", "ha3fn", "ha3ln", "ha3@gmail.com", 1, -2);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ha4un", "ha4pw", "ha4fn", "ha4ln", "ha4@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ha5un", "ha5pw", "ha5fn", "ha5ln", "ha5@gmail.com", 1);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-6, "ha6un", "ha6pw", "ha6fn", "ha6ln", "ha6@gmail.com", 1);

--dummy podaci za hotele
insert into hotel(id, address, description, name, rating) values (0, "Bulevar oslobodjenja 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque suscipit aliquam eleifend. In consectetur sed velit nec dapibus. Mauris nec mauris id diam hendrerit ultrices. Ut nec arcu et arcu porttitor fermentum. Nunc varius, arcu in vestibulum luctus, nunc lorem aliquet neque, at feugiat sem neque nec leo. Etiam bibendum erat quis tortor eleifend, sed bibendum libero feugiat. Nam feugiat ac diam ac tincidunt. Phasellus justo ex, ultrices vel volutpat quis, consectetur id quam. Quisque at ullamcorper augue, ut pretium odio.", "Royal Palace", 0);
insert into room(id, discount, floor, num_of_beds, number, room_type, hotel_id, room_price) values (-1, 0, 1, 1, 101, 0, 0, 10);
insert into room(id, discount, floor, num_of_beds, number, room_type, hotel_id, room_price) values (-2, 0, 1, 2, 102, 1, 0, 18);
insert into room(id, discount, floor, num_of_beds, number, room_type, hotel_id, room_price) values (-3, 0, 1, 2, 103, 1, 0, 20);

insert into hotel(id, address, description, name, rating) values (-1, "Vladimira Popovica 10", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dignissim augue eleifend ante sagittis, quis dapibus ex molestie. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc tempus commodo nibh, vel pharetra metus laoreet ut. Nulla et ante vel diam auctor mollis. Vivamus vulputate mattis lacus, ut tempor neque posuere at. Donec a tortor suscipit velit venenatis iaculis. Aliquam et ex rutrum, pellentesque ipsum at, dictum nibh. Integer arcu mi, pellentesque et urna in, vestibulum interdum mauris. Integer ut velit gravida, ullamcorper mauris vitae, imperdiet leo. Nam consequat, leo et porta ultricies, tortor velit rutrum tortor, venenatis commodo ipsum sapien sed nisi. Integer ut congue dolor. Nulla lacinia molestie purus. In at dui nec sem faucibus vestibulum. Curabitur et libero auctor, sodales leo non, auctor ex. Maecenas egestas odio orci, nec feugiat lorem posuere eget. Suspendisse rhoncus ligula in rhoncus pretium.", "Crowne Plaza Belgrade", 0);
insert into hotel(id, address, description, name, rating) values (-2, "Haile Selassie Ave", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nunc tortor, tincidunt et blandit a, porttitor sed lorem. Nam accumsan vehicula lectus ac imperdiet. Sed enim tellus, mattis eget tempor at, aliquam id massa. Fusce quis tortor libero. Donec vel tincidunt dui, ut rutrum massa. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras sodales eget libero gravida egestas.", "InterContinental Lusaka", 0);



--dummy podaci za airline admin-a
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "aa1un", "aa1pw", "aa1fn", "aa1ln", "aa1@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "aa2un", "aa2pw", "aa2fn", "aa2ln", "aa2@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "aa3un", "aa3pw", "aa3fn", "aa3ln", "aa3@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "aa4un", "aa4pw", "aa4fn", "aa4ln", "aa4@gmail.com", 1);
insert into airline_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "aa5un", "aa5pw", "aa5fn", "aa5ln", "aa5@gmail.com", 1);

--dummy podaci za rent a car admin-a
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled, rent_a_car_id) values(-1, "ra1un", "ra1pw", "ra1fn", "ra1ln", "ra1@gmail.com", 1, 0);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-2, "ra2un", "ra2pw", "ra2fn", "ra2ln", "ra2@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-3, "ra3un", "ra3pw", "ra3fn", "ra3ln", "ra3@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-4, "ra4un", "ra4pw", "ra4fn", "ra4ln", "ra4@gmail.com", 1);
insert into rentacar_admin(id, username, password, firstName, lastName, email_id, is_enabled) values(-5, "ra5un", "ra5pw", "ra5fn", "ra5ln", "ra5@gmail.com", 1);


insert into registered_user(id, username, password, firstName, lastName, email_id, is_enabled) values(-1, "malidzo", "dsa", "Milos", "Malidza", "milosmalidzaa@gmail.com", 1);


insert into rentacar(id, address, description, name, rating) values(0, "dummy address", "Lorem ipsum dolor sit amet, consectetur voluptate velit esse cillum dolore eu fugiat nulla pariatur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Economy Car Rentals", 0);
insert into rentacar(id, address, description, name, rating) values(-1, "dummy address", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Rent A Car Novi Sad", 0);
insert into rentacar(id, address, description, name, rating) values(-2, "dummy address", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed doquat. Duis aute irure dolot esse cillum dolore eu fugiat nulla pariatur.", "Rent A Car Beograd", 0);


insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(0, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Mazda 3 2019", 5, 5, 15, 0, 0);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-1, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Maruti Suzuki Ciaz", 5, 5, 17, 1, 0);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-2, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Datsun GO+", 5, 5, 12, 2, 0);
                        
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-9, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Mazda 6 2018", 5, 5, 25, 0, 0);

                        
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-3, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Mazda 3 2019", 3, 5, 15, 0, 0);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-4, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Maruti Suzuki Ciaz", 3, 5, 17, 1, 0);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-5, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Datsun GO+", 3, 5, 12, 2, 0);
                        
					
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-6, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Golf 5", 5, 5, 15, 0, -1);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-7, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Tesla Model S", 5, 5, 17, 1, -1);
insert into vehicle(id, archived, description, name, num_of_doors, numb_of_seats, price_per_day, vehicle_type, rentacar_id) values(-8, 0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
						et dolore magna aliqua.", "Suzuki Vitara", 5, 5, 12, 2, -1);
                    
insert into rentacar_branch_office(id, address, name, rentacar_id) values(-1, "1225  Layman Avenue", "Statesboro", 0);

insert into rentacar_branch_office(id, address, name, rentacar_id) values(-2, "4393  Chandler Hollow Road", "Pittsburgh", 0);
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                        
                        
                        
                        
                        
                        
                        
                        
