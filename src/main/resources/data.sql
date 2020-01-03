insert into station(name) values ('maastro');
insert into station(name) values ('catharina');
insert into station(name) values ('maasstad');
insert into station(name) values ('martini');
insert into station(name) values ('ovlg');
insert into train(id, name, docker_image_url, owner_id, calculation_status) values (1,'statistics', 'dockerurl', '03838bb4-8103-4a98-a9c3-d4848b13b3f6', 2);
insert into task(id, train_id, station_id, result, creation_timestamp, calculation_status)
        values (1, 1, 1, '{life-the-universe-and-everything: 42}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1);
insert into task(id, train_id, station_id, result, creation_timestamp, calculation_status)
        values (2, 1, 2, '{life-the-universe-and-everything: 11}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1);
insert into task(id, train_id, station_id, result, creation_timestamp, calculation_status)
        values (3, 1, 3, '{life-the-universe-and-everything: 1}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1);
insert into task(id, train_id, station_id, result, creation_timestamp, calculation_status)
        values (4, 1, 4, '{life-the-universe-and-everything: 12}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1);
insert into task(id, train_id, station_id, result, creation_timestamp, calculation_status)
        values (5, 1, 5, '{life-the-universe-and-everything: 32}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1);