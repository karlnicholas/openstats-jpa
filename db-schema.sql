
    alter table Assembly 
        drop constraint FK_l9wdewlweysuvf5unson90tht;
    alter table Assembly_aggregates 
        drop constraint FK_qy7mpypor1nni9my54dplt0yo;
    alter table Assembly_computations 
        drop constraint FK_48cubw44i56uhlt05i3vrlied;
    alter table District_Legislator 
        drop constraint FK_2uffod2ldeb2cv0yvsf750siv;
    alter table District_Legislator 
        drop constraint FK_5pm9sjbqss6dnn64r4sraenc3;
    alter table District_aggregates 
        drop constraint FK_f8bjk7728cgug743jif4pneiy;
    alter table District_computations 
        drop constraint FK_ksro43goqx3hq1uggsfnkcs1h;
    alter table Districts_District 
        drop constraint FK_fiygsso958eeod0puoi09e83n;
    alter table Districts_District 
        drop constraint FK_hdk54mjpm5y8tuwj1crse9oqe;
    alter table GroupInfo_groupDescriptions 
        drop constraint FK_h1e036v12ho97fe283d63jkkf;
    alter table GroupInfo_groupLabels 
        drop constraint FK_dksf6752wqb8v0s2tw6wnml9o;
    alter table assembly_aggregategroupmap 
        drop constraint FK_9poknpmgasyyp1obdt5qoe5le;
    alter table assembly_aggregategroupmap 
        drop constraint FK_ajof2kgqyi3yprhc5c3v8ioxf;
    alter table assembly_computationgroupmap 
        drop constraint FK_ob68eulskxrjds0u3vhe0u84b;
    alter table assembly_computationgroupmap 
        drop constraint FK_9d4wqno205uf3pyai85yj7crt;
    alter table districts_aggregategroupmap 
        drop constraint FK_mi5hp56igll0b9vrfm72wnje;
    alter table districts_aggregategroupmap 
        drop constraint FK_4rrfe3663pn4bp9k5k6fvg8ap;
    alter table districts_computationgroupmap 
        drop constraint FK_g6mwljbhrpt40bac4xomj34e7;
    alter table districts_computationgroupmap 
        drop constraint FK_idg2fsr5m4nbcxt146jasnb8k;
    drop table if exists Assembly cascade;
    drop table if exists Assembly_aggregates cascade;
    drop table if exists Assembly_computations cascade;
    drop table if exists District cascade;
    drop table if exists District_Legislator cascade;
    drop table if exists District_aggregates cascade;
    drop table if exists District_computations cascade;
    drop table if exists Districts cascade;
    drop table if exists Districts_District cascade;
    drop table if exists GroupInfo cascade;
    drop table if exists GroupInfo_groupDescriptions cascade;
    drop table if exists GroupInfo_groupLabels cascade;
    drop table if exists Legislator cascade;
    drop table if exists assembly_aggregategroupmap cascade;
    drop table if exists assembly_computationgroupmap cascade;
    drop table if exists districts_aggregategroupmap cascade;
    drop table if exists districts_computationgroupmap cascade;
    drop sequence hibernate_sequence;
    create table Assembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
    );
    create table Assembly_aggregates (
        Assembly_id int8 not null,
        aggregates bytea,
        aggregates_KEY varchar(255),
        primary key (Assembly_id, aggregates_KEY)
    );
    create table Assembly_computations (
        Assembly_id int8 not null,
        computations bytea,
        computations_KEY varchar(255),
        primary key (Assembly_id, computations_KEY)
    );
    create table District (
        id int8 not null,
        chamber varchar(255),
        district varchar(255),
        primary key (id)
    );
    create table District_Legislator (
        District_id int8 not null,
        legislators_id int8 not null
    );
    create table District_aggregates (
        District_id int8 not null,
        aggregates bytea,
        aggregates_KEY varchar(255),
        primary key (District_id, aggregates_KEY)
    );
    create table District_computations (
        District_id int8 not null,
        computations bytea,
        computations_KEY varchar(255),
        primary key (District_id, computations_KEY)
    );
    create table Districts (
        id int8 not null,
        primary key (id)
    );
    create table Districts_District (
        Districts_id int8 not null,
        districtList_id int8 not null
    );
    create table GroupInfo (
        id int8 not null,
        primary key (id)
    );
    create table GroupInfo_groupDescriptions (
        GroupInfo_id int8 not null,
        groupDescriptions varchar(255),
        groupDescriptions_ORDER int4 not null,
        primary key (GroupInfo_id, groupDescriptions_ORDER)
    );
    create table GroupInfo_groupLabels (
        GroupInfo_id int8 not null,
        groupLabels varchar(255),
        groupLabels_ORDER int4 not null,
        primary key (GroupInfo_id, groupLabels_ORDER)
    );
    create table Legislator (
        id int8 not null,
        name varchar(255),
        party varchar(255),
        primary key (id)
    );
    create table assembly_aggregategroupmap (
        Assembly_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY varchar(255),
        primary key (Assembly_id, aggregateGroupMap_KEY)
    );
    create table assembly_computationgroupmap (
        Assembly_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY varchar(255),
        primary key (Assembly_id, computationGroupMap_KEY)
    );
    create table districts_aggregategroupmap (
        Districts_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY varchar(255),
        primary key (Districts_id, aggregateGroupMap_KEY)
    );
    create table districts_computationgroupmap (
        Districts_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY varchar(255),
        primary key (Districts_id, computationGroupMap_KEY)
    );
    alter table District_Legislator 
        add constraint UK_2uffod2ldeb2cv0yvsf750siv unique (legislators_id);
    alter table Districts_District 
        add constraint UK_fiygsso958eeod0puoi09e83n unique (districtList_id);
    alter table assembly_aggregategroupmap 
        add constraint UK_9poknpmgasyyp1obdt5qoe5le unique (aggregateGroupMap_id);
    alter table assembly_computationgroupmap 
        add constraint UK_ob68eulskxrjds0u3vhe0u84b unique (computationGroupMap_id);
    alter table districts_aggregategroupmap 
        add constraint UK_mi5hp56igll0b9vrfm72wnje unique (aggregateGroupMap_id);
    alter table districts_computationgroupmap 
        add constraint UK_g6mwljbhrpt40bac4xomj34e7 unique (computationGroupMap_id);
    alter table Assembly 
        add constraint FK_l9wdewlweysuvf5unson90tht 
        foreign key (districts_id) 
        references Districts;
    alter table Assembly_aggregates 
        add constraint FK_qy7mpypor1nni9my54dplt0yo 
        foreign key (Assembly_id) 
        references Assembly;
    alter table Assembly_computations 
        add constraint FK_48cubw44i56uhlt05i3vrlied 
        foreign key (Assembly_id) 
        references Assembly;
    alter table District_Legislator 
        add constraint FK_2uffod2ldeb2cv0yvsf750siv 
        foreign key (legislators_id) 
        references Legislator;
    alter table District_Legislator 
        add constraint FK_5pm9sjbqss6dnn64r4sraenc3 
        foreign key (District_id) 
        references District;
    alter table District_aggregates 
        add constraint FK_f8bjk7728cgug743jif4pneiy 
        foreign key (District_id) 
        references District;
    alter table District_computations 
        add constraint FK_ksro43goqx3hq1uggsfnkcs1h 
        foreign key (District_id) 
        references District;
    alter table Districts_District 
        add constraint FK_fiygsso958eeod0puoi09e83n 
        foreign key (districtList_id) 
        references District;
    alter table Districts_District 
        add constraint FK_hdk54mjpm5y8tuwj1crse9oqe 
        foreign key (Districts_id) 
        references Districts;
    alter table GroupInfo_groupDescriptions 
        add constraint FK_h1e036v12ho97fe283d63jkkf 
        foreign key (GroupInfo_id) 
        references GroupInfo;
    alter table GroupInfo_groupLabels 
        add constraint FK_dksf6752wqb8v0s2tw6wnml9o 
        foreign key (GroupInfo_id) 
        references GroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_9poknpmgasyyp1obdt5qoe5le 
        foreign key (aggregateGroupMap_id) 
        references GroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_ajof2kgqyi3yprhc5c3v8ioxf 
        foreign key (Assembly_id) 
        references Assembly;
    alter table assembly_computationgroupmap 
        add constraint FK_ob68eulskxrjds0u3vhe0u84b 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table assembly_computationgroupmap 
        add constraint FK_9d4wqno205uf3pyai85yj7crt 
        foreign key (Assembly_id) 
        references Assembly;
    alter table districts_aggregategroupmap 
        add constraint FK_mi5hp56igll0b9vrfm72wnje 
        foreign key (aggregateGroupMap_id) 
        references GroupInfo;
    alter table districts_aggregategroupmap 
        add constraint FK_4rrfe3663pn4bp9k5k6fvg8ap 
        foreign key (Districts_id) 
        references Districts;
    alter table districts_computationgroupmap 
        add constraint FK_g6mwljbhrpt40bac4xomj34e7 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table districts_computationgroupmap 
        add constraint FK_idg2fsr5m4nbcxt146jasnb8k 
        foreign key (Districts_id) 
        references Districts;
    create sequence hibernate_sequence;