insert into station(id, name) values (1, 'testclient');
insert into train(id, name, docker_image_url, owner_id, calculation_status, current_iteration)
values (1,'statistics', 'registry.gitlab.com/medicaldataworks/railway/prototypetrain:master', '4f2f0ceb-b794-4e2d-a7ec-45b51b54ba40', 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master, iteration)
        values (1, 1, 1, '',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 1, 1, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master, iteration)
        values (2, 1, 1, '4',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 4, 0, 0);
insert into task(id, train_id, station_id, input, creation_timestamp, calculation_status, master, iteration)
        values (3, 1, 1, '2',
        TO_TIMESTAMP('2019-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 4, 0, 0);