-- Table restaurant
DROP TABLE restaurant;
DROP TABLE tabl CASCADE CONSTRAINTS;
DROP TABLE reservation CASCADE CONSTRAINTS;

create table restaurant
(
    idRestaurant   number(4),
    nomRestaurant  varchar2(255),
    adrRestaurant  varchar2(255),
    latRestaurant  number(17, 15),
    lontRestaurant number(17, 15),
    primary key (idRestaurant)
);

insert into restaurant
values (1, 'Retro Gusto', '40 Rue Stanislas, 54000 Nancy', 48.69279935155353, 6.180580036508078);
insert into restaurant
values (2, 'Jean L amour', '7-9 Pl. Stanislas, 54000 Nancy', 48.693865009267085, 6.182571295334463);
insert into restaurant
values (3, 'Grand Café Joy', '1 Pl. Stanislas, 54000 Nancy', 48.69303152361996, 6.1826400096399);
insert into restaurant
values (4, 'Retro Gusto', '45 Rue Saint-Dizier, 54000 Nancy', 48.69045784799938, 6.182854068019691);
insert into restaurant
values (5, 'Coté Sushi', '18 Pl. Henri Mengin, 54000 Nancy', 48.68961720000001, 6.182128135594706);

-- Table tabl
create table tabl
(
    numtab       number(4),
    idRestaurant number(4),
    nbplace      number(2),
    primary key (numtab)
);

insert into tabl
values (1, 1, 2);
insert into tabl
values (2, 1, 6);
insert into tabl
values (3, 1, 8);
insert into tabl
values (4, 1, 4);
insert into tabl
values (5, 2, 6);
insert into tabl
values (6, 2, 12);
insert into tabl
values (7, 2, 4);
insert into tabl
values (8, 2, 4);
insert into tabl
values (9, 3, 4);
insert into tabl
values (10, 3, 8);
insert into tabl
values (11, 3, 2);
insert into tabl
values (12, 4, 10);
insert into tabl
values (13, 4, 4);
insert into tabl
values (14, 4, 6);
insert into tabl
values (15, 5, 12);
insert into tabl
values (16, 5, 6);

-- Table reservation
create table reservation
(
    idReservation number(4),
    idRestaurant  number(4),
    numtab        number(4),
    nom           varchar2(50),
    prenom        varchar2(50),
    nbpers        number(2),
    numTelephone  number(10),
    primary key (idReservation)
);

insert into reservation
values (1, 1, 2, 'Alexis', 'ZOTT', 3, 0650247595);
insert into reservation
values (2, 2, 4, 'Gaëtan', 'LEMEUNIER', 6, 0948506254);
insert into reservation
values (3, 3, 9, 'Thomas', 'Charpentier', 8, 0128546324);
insert into reservation
values (4, 4, 12, 'Mathieu', 'DZIEZUK', 4, 0248963547);



ALTER TABLE reservation
ADD dateReservation date

alter table tabl
    add (foreign key
        (idRestaurant)
        references restaurant (idRestaurant));

alter table reservation
    add constraint fk_reservation_restaurant
        foreign key (idRestaurant)
        references restaurant (idRestaurant);

alter table reservation
    add constraint fk_reservation_tabl
        foreign key (numtab)
        references tabl (numtab);