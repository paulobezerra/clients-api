insert into locations (id, name, type, parent_location_id)
values (1, 'Brasil', 'COUNTRY', null);

insert into locations (id, name, type, parent_location_id)
values (2, 'Mato Grosso do Sul', 'STATE', 1);

insert into locations (id, name, type, parent_location_id)
values (3, 'Campo Grande', 'CITY', 2);

insert into addresses (id, public_place, property_number, zip_code, location_id)
values (1, 'St Angel', '66', '12345678', 3);

insert into people (id, type, category, name, address_id)
values (1, 'NATURAL', 'CLIENT', 'Joe Doe', 1);

insert into people (id, type, category, name, address_id)
values (2, 'LEGAL', 'CLIENT', 'ACME Inc.', 1);

insert into natural_people (person_id, birth_date, gender, marital_status, spouse_name, mother_name, father_name, rg, cpf)
values (1, now(), 'MALE', 'MARRIED', 'Rose Doe', 'Mary Doe', 'Bob Doe', '1234565', '12345678912');

insert into legal_people (person_id, opening_date, ie, cnpj)
values (2, now(), '1234565', '12345678912');

insert into contacts (legal_person_id, natural_person_id)
values (2, 1);

insert into phones(id, type, number, person_id)
values (1, 'Residential', '1234568987', 1);

insert into phones(id, type, number, person_id)
values (2, 'Telemarketing', '1234568987', 2);