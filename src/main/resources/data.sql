insert into station(name) values ('maastro');
insert into station(name) values ('catharina');
insert into station(name) values ('maasstad');
insert into station(name) values ('martini');
insert into station(name) values ('ovlg');
insert into train(id, name, docker_image_url, owner_id, calculation_status) values (1,'statistics', 'registry.gitlab.com/medicaldataworks/railway/prototypetrain:master', '4f2f0ceb-b794-4e2d-a7ec-45b51b54ba40', 2);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master)
        values (1, 1, 1, '{life-the-universe-and-everything: 42}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master)
        values (2, 1, 2, '{life-the-universe-and-everything: 11}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master)
        values (3, 1, 3, '{life-the-universe-and-everything: 1}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master)
        values (4, 1, 4, '{life-the-universe-and-everything: 12}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master)
        values (5, 1, 5, '{life-the-universe-and-everything: 32}',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 0);