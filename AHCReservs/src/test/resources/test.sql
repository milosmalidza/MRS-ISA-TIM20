-- Clear data and insert queries

truncate table hotel_admin;
truncate table hotel;

insert into hotel(id, address, description, name, rating) values (0, "Bulevar oslobodjenja 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "Royal Palace", 0);
insert into hotel_admin(id, username, password, firstName, lastName, email_id, is_enabled, password_changed, hotel_id) values(-1, "ha1un", "ha1pw", "ha1fn", "ha1ln", "ha1@gmail.com", 1, 1, 0);
