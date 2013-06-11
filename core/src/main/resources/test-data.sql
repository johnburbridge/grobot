insert into BOTS (ID, NAME, ADDRESS) values 
    ('a85765e0-7e90-47b9-aea3-7da16117d274','jburbridge-dev1', 'localhost'),
    ('9741afbb-a1be-47e0-9aa9-8a7f3702ffea','fake-host1', 'fake-host1.dummy.domain'),
    ('686ffd94-cbf7-4767-bf77-9d529b0087e7','fake-host2', 'fake-host2.dummy.domain'),
    ('a56fde5f-0033-4050-8964-2b8d7470ee28','fake-host3', 'fake-host3.dummy.domain'),
    ('952ed06c-0283-4866-b8e6-d16ca6f57312','fake-host4', 'fake-host4.dummy.domain'),
    ('78bb9ccc-ca3d-4e9d-90b6-931c854ef7ef','fake-host5', 'fake-host5.dummy.domain'),
    ('b3539be2-bf93-4353-8bf4-8ee57c1e9161','fake-host6', 'fake-host6.dummy.domain'),
    ('515728ad-669b-4f37-80b8-54264201786d','fake-host7', 'fake-host7.dummy.domain'),
    ('2404cbd8-6a18-4170-ad99-2f416f52b06b','fake-host8', 'fake-host8.dummy.domain'),
    ('54ec2667-ef87-4b35-8fc0-0c31e9b8a1d6','fake-host9', 'fake-host9.dummy.domain'),
    ('fe28f806-62a5-493e-a1b0-4af7aedf0988','fake-host10', 'fake-host10.dummy.domain'),
    ('7579fc94-9365-492d-9f2a-8e72657c848c','fake-host11', 'fake-host11.dummy.domain'),
    ('c1e9ae5b-0182-48db-a649-dae6251a2214','fake-host12', 'fake-host12.dummy.domain'),
    ('5d0b6478-76af-4715-8e17-bfec442b4004','fake-host13', 'fake-host13.dummy.domain'),
    ('5ed75570-204a-48b8-96db-2ea041fdd1a3','fake-host14', 'fake-host14.dummy.domain'),
    ('3a264128-ddfa-4746-b340-683a309a082d','fake-host15', 'fake-host15.dummy.domain'),
    ('3310d653-8896-45ab-a50e-962ebfb6f6de','fake-host16', 'fake-host16.dummy.domain'),
    ('137bf2bd-4d32-4908-8ef9-838951d1bcf5','fake-host17', 'fake-host17.dummy.domain'),
    ('401f90bc-9025-474f-ad41-6b19e8f1132f','fake-host18', 'fake-host18.dummy.domain'),
    ('c75992f7-f193-41f1-b3f5-9a8a572eb38c','fake-host19', 'fake-host19.dummy.domain'),
    ('22f0514b-1c52-4275-ac16-b01c8295ecb0','fake-host20', 'fake-host20.dummy.domain'),
    ('5a2e8105-21d4-40cc-9b0c-51cb07a85d2b','fake-host21', 'fake-host21.dummy.domain'),
    ('a9da349b-71bf-43be-989d-b43ec46e751d','fake-host22', 'fake-host22.dummy.domain'),
    ('34709190-44b8-43ce-85f0-51cbde06f8ec','fake-host23', 'fake-host23.dummy.domain'),
    ('22e3962b-cec0-46cd-93a2-63f9d4dac5b9','fake-host24', 'fake-host24.dummy.domain'),
    ('1d9fa89a-b880-4ca2-b9fb-5e6a72516361','fake-host25', 'fake-host25.dummy.domain'),
    ('5bda599f-64b4-493d-b23e-7ade0ec21fd9','fake-host26', 'fake-host26.dummy.domain'),
    ('44307d3b-0602-412e-ba32-6a22a7bd569e','fake-host27', 'fake-host27.dummy.domain'),
    ('669aaba6-2cd0-4aba-b4ae-70181ab2f4ad','fake-host28', 'fake-host28.dummy.domain'),
    ('e0b1cbc2-65b8-479c-8589-b054860d67e8','fake-host29', 'fake-host29.dummy.domain'),
    ('e2fabf50-404d-44e3-8d58-47ba60dbbb08','fake-host30', 'fake-host30.dummy.domain'),
    ('acf26094-d520-4cde-85bc-bf892f393511','fake-host31', 'fake-host31.dummy.domain'),
    ('8b9d5035-dafd-4923-8f2d-46cea76058bc','fake-host32', 'fake-host32.dummy.domain'),
    ('3363afa3-8bc8-462a-bbbd-ee4c25193fed','fake-host33', 'fake-host33.dummy.domain'),
    ('4c88d4aa-498c-4183-9b7d-a61cf908455a','fake-host34', 'fake-host34.dummy.domain');

insert into BOT_GROUPS (ID, NAME, DESCRIPTION, ACTIVE) values 
    ('225c172b-950f-4c24-9ee7-4d3b4ea1cf55','fake-group1', 'The first group', true),
    ('1848d4b8-93b9-4970-be41-b366517af44b','fake-group2', 'The second group', true),
    ('f2c1fafa-af63-499b-8bdb-d7a0df26837f','fake-group3', 'The third group', false);

insert into BOT_GROUP_MEMBERS (BOT_GROUP_ID, BOT_ID) values 
    ('225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'a85765e0-7e90-47b9-aea3-7da16117d274'),
    ('225c172b-950f-4c24-9ee7-4d3b4ea1cf55', '9741afbb-a1be-47e0-9aa9-8a7f3702ffea'),
    ('225c172b-950f-4c24-9ee7-4d3b4ea1cf55', '686ffd94-cbf7-4767-bf77-9d529b0087e7'),
    ('1848d4b8-93b9-4970-be41-b366517af44b', 'a56fde5f-0033-4050-8964-2b8d7470ee28'),
    ('1848d4b8-93b9-4970-be41-b366517af44b', '952ed06c-0283-4866-b8e6-d16ca6f57312');

insert into SCRIPTS (ID, PATH, BODY) values 
    ('225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'foo', 'bar');

insert into TASKS (ID, NAME, SCRIPT_ID, ARGUMENTS) values
    ('d1f406ac-a9d2-40cf-a9b3-64e725f5bb36', 'fake-task1', '225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'foo bar'),
    ('88f0d086-e256-4c01-b9d5-d529480e7217', 'fake-task2', '225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'foo bar baz'),
    ('8314a775-50fc-4ec5-bc2c-2df0531a0f5c', 'fake-task3', '225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'bar baz'),
    ('1e95d712-b9b2-4e52-b598-b67565ff8182', 'fake-task4', '225c172b-950f-4c24-9ee7-4d3b4ea1cf55', 'foo baz'),
    ('56d66369-4b5f-4b31-bd71-e3f826d6a46c', 'fake-task5', '225c172b-950f-4c24-9ee7-4d3b4ea1cf55', null);

insert into TASK_EXECUTIONS (ID, TASK_ID) values
    ('9521a922-cc00-4126-89ce-818ca76d3eb7', 'd1f406ac-a9d2-40cf-a9b3-64e725f5bb36'),
    ('24a9587a-dbe9-452f-bc2a-3fa7e5e7f0f8', 'd1f406ac-a9d2-40cf-a9b3-64e725f5bb36'),
    ('7510156b-a3ec-453d-97f8-4b3fae1a38e8', 'd1f406ac-a9d2-40cf-a9b3-64e725f5bb36'),
    ('96684384-f684-4bfc-9d88-3ccb99daf491', '88f0d086-e256-4c01-b9d5-d529480e7217'),
    ('a5f4d3f2-a353-46a5-a908-f493ec399f0d', '88f0d086-e256-4c01-b9d5-d529480e7217');
    
select * from BOTS;

