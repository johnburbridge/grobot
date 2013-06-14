create table BOTS (
  ID varchar(36) not null,
  NAME varchar(60) not null, 
  ADDRESS varchar(40) not null,
  REGISTERED DATE,
  ACTIVE boolean default false,
  unique UQ_BOTS_1 (ID), 
  unique UQ_BOTS_2 (NAME), 
  primary key (ID) 
); 

create table BOT_GROUPS (
  ID varchar(36) not null,
  NAME varchar(60) not null,
  DESCRIPTION varchar(250),
  ACTIVE boolean default false,
  unique UQ_BOT_GROUPS_1 (ID), 
  unique UQ_BOT_GROUPS_2 (NAME), 
  primary key (ID) 
); 

create table BOT_GROUP_MEMBERS (
  BOT_GROUP_ID varchar(36) not null, 
  BOT_ID varchar(36) not null, 
  constraint FK_BOT_GROUP_MEMBERS_1 foreign key (BOT_GROUP_ID)
    references BOT_GROUPS (ID) on delete cascade,
  constraint FK_BOT_GROUP_MEMBERS_2 foreign key (BOT_ID)
    references BOTS (ID) on delete cascade,
  primary key (BOT_GROUP_ID, BOT_ID) 
); 

create table SCRIPTS (
  ID varchar(36) not null,
  PATH varchar(255) not null, 
  BODY CLOB, 
  unique UQ_SCRIPTS_1 (ID), 
  unique UQ_SCRIPTS_2 (PATH), 
  primary key (ID) 
);

create table TASKS (
  ID varchar(36) not null,
  NAME varchar(60) not null, 
  SCRIPT_ID varchar(36),
  SCRIPT_NAME varchar(150),
  ARGUMENTS varchar(120), 
  unique UQ_TASKS_1 (ID), 
  unique UQ_TASKS_2 (NAME), 
  constraint FK_TASKS_1 foreign key (SCRIPT_ID)
    references SCRIPTS(ID) on delete cascade,
  primary key (ID) 
);

create table TASK_EXECUTIONS (
  ID varchar(36) not null,
  TASK_ID varchar(36) not null,
  SCHEDULED_START_TIME DATE,
  ACTUAL_START_TIME DATE,
  END_TIME DATE,
  unique UQ_TASK_EXECUTIONS_1 (ID), 
  constraint FK_TASK_EXECUTIONS_1 foreign key (TASK_ID)
    references TASKS (ID) on delete cascade,
  primary key (ID) 
);

