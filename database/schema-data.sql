
drop database if exists calorie_tracker;
create database calorie_tracker;

use calorie_tracker;

create table log_entry_type (
	log_entry_type_id int primary key auto_increment,
    type varchar(100) not null unique
);

create table log_entry (
	log_entry_id int primary key auto_increment,
    logged_on datetime not null,
    log_entry_type_id int not null,
    description varchar(100) not null,
    calories int not null,
    constraint fk_log_entry_log_entry_type_id
        foreign key (log_entry_type_id)
        references log_entry_type(log_entry_type_id)
);

insert into log_entry_type (log_entry_type_id, type)
	values
	(1, 'Breakfast'),
	(2, 'Lunch'),
	(3, 'Dinner'),
	(4, 'Snack'),
	(5, 'Second Breakfast');

insert into log_entry (logged_on, log_entry_type_id, description, calories)
    values
    ('2020-01-01 9:00', 1, 'Scrambled eggs', 210),
    ('2020-01-01 12:00', 2, 'Tuna fish salad', 500),
    ('2020-01-01 18:00', 3, 'Steak', 1000);
