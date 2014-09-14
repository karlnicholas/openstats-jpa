
    alter table Aggregate_labels 
        drop constraint FK_nysr57f5ydnp403697yowt1cg;
    alter table Aggregate_values 
        drop constraint FK_4w7tx3fji5ncip3omc1eyujep;
    alter table Aggregates_Aggregate 
        drop constraint FK_soetw1upc09it8cniu0cso7i8;
    alter table Aggregates_Aggregate 
        drop constraint FK_78nfnn8yvd8yb94m9r37a5cee;
    alter table Aggregates_descriptions 
        drop constraint FK_1d8qnigcphh0s42dcg224pwkl;
    alter table Aggregates_groups 
        drop constraint FK_cy19jpjnkoybh2vuyp5gevadj;
    alter table Computation_labels 
        drop constraint FK_2f7rk3381yu7ygjb6nykbcdxf;
    alter table Computation_values 
        drop constraint FK_8ghfiwqhrn18ibbgo0to4jdpf;
    alter table Computations_Computation 
        drop constraint FK_8ns9foqyu7pb8g5rh78j8jkeq;
    alter table Computations_Computation 
        drop constraint FK_fr3q7jdn6sew8wbp9foiogbnj;
    alter table Computations_descriptions 
        drop constraint FK_gho1imb0o3noo36x442mhxi1n;
    alter table Computations_groups 
        drop constraint FK_84khleoxhrpyeen3r0yvqj7ks;
    alter table District_Legislator 
        drop constraint FK_2uffod2ldeb2cv0yvsf750siv;
    alter table District_Legislator 
        drop constraint FK_5pm9sjbqss6dnn64r4sraenc3;
    alter table Districts_District 
        drop constraint FK_2qrianb6h0ucpyq77at4upauv;
    alter table Districts_District 
        drop constraint FK_hdk54mjpm5y8tuwj1crse9oqe;
    drop table if exists Aggregate cascade;
    drop table if exists Aggregate_labels cascade;
    drop table if exists Aggregate_values cascade;
    drop table if exists Aggregates cascade;
    drop table if exists Aggregates_Aggregate cascade;
    drop table if exists Aggregates_descriptions cascade;
    drop table if exists Aggregates_groups cascade;
    drop table if exists Computation cascade;
    drop table if exists Computation_labels cascade;
    drop table if exists Computation_values cascade;
    drop table if exists Computations cascade;
    drop table if exists Computations_Computation cascade;
    drop table if exists Computations_descriptions cascade;
    drop table if exists Computations_groups cascade;
    drop table if exists District cascade;
    drop table if exists DistrictUserData cascade;
    drop table if exists District_Legislator cascade;
    drop table if exists Districts cascade;
    drop table if exists Districts_District cascade;
    drop table if exists Legislator cascade;
    drop table if exists Session cascade;
    drop table if exists SessionUserData cascade;
    drop sequence hibernate_sequence;
    create table Aggregate (
        id int8 not null,
        primary key (id)
    );
    create table Aggregate_labels (
        Aggregate_id int8 not null,
        labels varchar(255)
    );
    create table Aggregate_values (
        Aggregate_id int8 not null,
        values bytea,
        values_KEY varchar(255),
        primary key (Aggregate_id, values_KEY)
    );
    create table Aggregates (
        id int8 not null,
        primary key (id)
    );
    create table Aggregates_Aggregate (
        Aggregates_id int8 not null,
        aggregates_KEY varchar(255),
        primary key (Aggregates_id, aggregates_KEY)
    );
    create table Aggregates_descriptions (
        Aggregates_id int8 not null,
        descriptions bytea,
        descriptions_KEY varchar(255),
        primary key (Aggregates_id, descriptions_KEY)
    );
    create table Aggregates_groups (
        Aggregates_id int8 not null,
        groups bytea,
        groups_KEY varchar(255),
        primary key (Aggregates_id, groups_KEY)
    );
    create table Computation (
        id int8 not null,
        primary key (id)
    );
    create table Computation_labels (
        Computation_id int8 not null,
        labels varchar(255)
    );
    create table Computation_values (
        Computation_id int8 not null,
        values bytea,
        values_KEY varchar(255),
        primary key (Computation_id, values_KEY)
    );
    create table Computations (
        id int8 not null,
        primary key (id)
    );
    create table Computations_Computation (
        Computations_id int8 not null,
        computations_KEY varchar(255),
        primary key (Computations_id, computations_KEY)
    );
    create table Computations_descriptions (
        Computations_id int8 not null,
        descriptions bytea,
        descriptions_KEY varchar(255),
        primary key (Computations_id, descriptions_KEY)
    );
    create table Computations_groups (
        Computations_id int8 not null,
        groups bytea,
        groups_KEY varchar(255),
        primary key (Computations_id, groups_KEY)
    );
    create table District (
        id int8 not null,
        chamber varchar(255),
        district varchar(255),
        primary key (id)
    );
    create table DistrictUserData (
        id int8 not null,
        primary key (id)
    );
    create table District_Legislator (
        District_id int8 not null,
        legislators_id int8 not null
    );
    create table Districts (
        id int8 not null,
        userData bytea,
        primary key (id)
    );
    create table Districts_District (
        Districts_id int8 not null
    );
    create table Legislator (
        id int8 not null,
        name varchar(255),
        party varchar(255),
        primary key (id)
    );
    create table Session (
        id int8 not null,
        districts bytea,
        session varchar(255),
        state varchar(255),
        userData bytea,
        primary key (id)
    );
    create table SessionUserData (
        id int8 not null,
        primary key (id)
    );
    alter table Aggregates_Aggregate 
        add constraint UK_soetw1upc09it8cniu0cso7i8 unique (aggregates_id);
    alter table Computations_Computation 
        add constraint UK_8ns9foqyu7pb8g5rh78j8jkeq unique (computations_id);
    alter table District_Legislator 
        add constraint UK_2uffod2ldeb2cv0yvsf750siv unique (legislators_id);
    alter table Districts_District 
        add constraint UK_2qrianb6h0ucpyq77at4upauv unique (districts_id);
    alter table Aggregate_labels 
        add constraint FK_nysr57f5ydnp403697yowt1cg 
        foreign key (Aggregate_id) 
        references Aggregate;
    alter table Aggregate_values 
        add constraint FK_4w7tx3fji5ncip3omc1eyujep 
        foreign key (Aggregate_id) 
        references Aggregate;
    alter table Aggregates_Aggregate 
        add constraint FK_soetw1upc09it8cniu0cso7i8 
        foreign key (aggregates_id) 
        references Aggregate;
    alter table Aggregates_Aggregate 
        add constraint FK_78nfnn8yvd8yb94m9r37a5cee 
        foreign key (Aggregates_id) 
        references Aggregates;
    alter table Aggregates_descriptions 
        add constraint FK_1d8qnigcphh0s42dcg224pwkl 
        foreign key (Aggregates_id) 
        references Aggregates;
    alter table Aggregates_groups 
        add constraint FK_cy19jpjnkoybh2vuyp5gevadj 
        foreign key (Aggregates_id) 
        references Aggregates;
    alter table Computation_labels 
        add constraint FK_2f7rk3381yu7ygjb6nykbcdxf 
        foreign key (Computation_id) 
        references Computation;
    alter table Computation_values 
        add constraint FK_8ghfiwqhrn18ibbgo0to4jdpf 
        foreign key (Computation_id) 
        references Computation;
    alter table Computations_Computation 
        add constraint FK_8ns9foqyu7pb8g5rh78j8jkeq 
        foreign key (computations_id) 
        references Computation;
    alter table Computations_Computation 
        add constraint FK_fr3q7jdn6sew8wbp9foiogbnj 
        foreign key (Computations_id) 
        references Computations;
    alter table Computations_descriptions 
        add constraint FK_gho1imb0o3noo36x442mhxi1n 
        foreign key (Computations_id) 
        references Computations;
    alter table Computations_groups 
        add constraint FK_84khleoxhrpyeen3r0yvqj7ks 
        foreign key (Computations_id) 
        references Computations;
    alter table District_Legislator 
        add constraint FK_2uffod2ldeb2cv0yvsf750siv 
        foreign key (legislators_id) 
        references Legislator;
    alter table District_Legislator 
        add constraint FK_5pm9sjbqss6dnn64r4sraenc3 
        foreign key (District_id) 
        references District;
    alter table Districts_District 
        add constraint FK_2qrianb6h0ucpyq77at4upauv 
        foreign key (districts_id) 
        references District;
    alter table Districts_District 
        add constraint FK_hdk54mjpm5y8tuwj1crse9oqe 
        foreign key (Districts_id) 
        references Districts;
    create sequence hibernate_sequence;