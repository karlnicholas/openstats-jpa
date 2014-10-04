
    alter table AggregateValues_valueList 
        drop constraint FK_bgs8w1ud2yry8h1u3mmn515wg;
    alter table Assembly 
        drop constraint FK_l9wdewlweysuvf5unson90tht;
    alter table Assembly_AggregateValues 
        drop constraint FK_f6hs9i0i7ddkbe4tmfc8pbpyt;
    alter table Assembly_AggregateValues 
        drop constraint FK_hx5h0m2wpbdp4t864c7mc1h1o;
    alter table Assembly_AggregateValues 
        drop constraint FK_1awpf56yeexc0k5btvgghcw2e;
    alter table Assembly_ComputationValues 
        drop constraint FK_3qxfbc503gp8iybc2vmovtsiq;
    alter table Assembly_ComputationValues 
        drop constraint FK_ktjl43oamuld6nsenmdf5wux5;
    alter table Assembly_ComputationValues 
        drop constraint FK_bv60ifrd86p80nbbuslom31mu;
    alter table ComputationValues_valueList 
        drop constraint FK_ptgvnduywhc12eqgjpivyghry;
    alter table District_AggregateValues 
        drop constraint FK_rdnxu0p7owuxu6j0avevi9qh;
    alter table District_AggregateValues 
        drop constraint FK_ercmlj4fu87653ejlb8ypdq1a;
    alter table District_AggregateValues 
        drop constraint FK_9r168cmwvjkdu9mtqjbdlxvw7;
    alter table District_ComputationValues 
        drop constraint FK_dh0v5i1jqnpsiwk1paac77ifu;
    alter table District_ComputationValues 
        drop constraint FK_aicbwbc0cdmc7eap2lbg4ixfd;
    alter table District_ComputationValues 
        drop constraint FK_gegsebt5ratwvqiufmlcx3wba;
    alter table District_Legislator 
        drop constraint FK_2uffod2ldeb2cv0yvsf750siv;
    alter table District_Legislator 
        drop constraint FK_5pm9sjbqss6dnn64r4sraenc3;
    alter table Districts_District 
        drop constraint FK_fiygsso958eeod0puoi09e83n;
    alter table Districts_District 
        drop constraint FK_hdk54mjpm5y8tuwj1crse9oqe;
    alter table Districts_aggregateGroupMap 
        drop constraint FK_7lcawj0i3iktaqyeac6f0tfsf;
    alter table Districts_aggregateGroupMap 
        drop constraint FK_7vcghng0872p1vun0uiysvp3s;
    alter table Districts_aggregateGroupMap 
        drop constraint FK_4bv1rpdm0g7ijvy5qhasa00r;
    alter table Districts_computationGroupMap 
        drop constraint FK_f37ermi5hxg38oej5ynafxfmb;
    alter table Districts_computationGroupMap 
        drop constraint FK_okqck7l9lh1md36pdlnp2h6rp;
    alter table Districts_computationGroupMap 
        drop constraint FK_81og4u2kc9qf1vg0bbwg4l0r6;
    alter table GroupInfo_groupDescriptions 
        drop constraint FK_h1e036v12ho97fe283d63jkkf;
    alter table GroupInfo_groupLabels 
        drop constraint FK_dksf6752wqb8v0s2tw6wnml9o;
    alter table assembly_aggregategroupmap 
        drop constraint FK_9poknpmgasyyp1obdt5qoe5le;
    alter table assembly_aggregategroupmap 
        drop constraint FK_qqslcx0jhkp9ueo9faajjhwlr;
    alter table assembly_aggregategroupmap 
        drop constraint FK_ajof2kgqyi3yprhc5c3v8ioxf;
    alter table assembly_computationgroupmap 
        drop constraint FK_ob68eulskxrjds0u3vhe0u84b;
    alter table assembly_computationgroupmap 
        drop constraint FK_a7betyowd17b66w8rjiq36ypv;
    alter table assembly_computationgroupmap 
        drop constraint FK_9d4wqno205uf3pyai85yj7crt;
    drop table if exists AggregateValues cascade;
    drop table if exists AggregateValues_valueList cascade;
    drop table if exists Assembly cascade;
    drop table if exists Assembly_AggregateValues cascade;
    drop table if exists Assembly_ComputationValues cascade;
    drop table if exists ComputationValues cascade;
    drop table if exists ComputationValues_valueList cascade;
    drop table if exists District cascade;
    drop table if exists District_AggregateValues cascade;
    drop table if exists District_ComputationValues cascade;
    drop table if exists District_Legislator cascade;
    drop table if exists Districts cascade;
    drop table if exists Districts_District cascade;
    drop table if exists Districts_aggregateGroupMap cascade;
    drop table if exists Districts_computationGroupMap cascade;
    drop table if exists GroupInfo cascade;
    drop table if exists GroupInfo_groupDescriptions cascade;
    drop table if exists GroupInfo_groupLabels cascade;
    drop table if exists GroupName cascade;
    drop table if exists Legislator cascade;
    drop table if exists assembly_aggregategroupmap cascade;
    drop table if exists assembly_computationgroupmap cascade;
    drop sequence hibernate_sequence;
    create table AggregateValues (
        id int8 not null,
        primary key (id)
    );
    create table AggregateValues_valueList (
        AggregateValues_id int8 not null,
        valueList int8,
        valueList_ORDER int4 not null,
        primary key (AggregateValues_id, valueList_ORDER)
    );
    create table Assembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
    );
    create table Assembly_AggregateValues (
        Assembly_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (Assembly_id, aggregateMap_KEY)
    );
    create table Assembly_ComputationValues (
        Assembly_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (Assembly_id, computationMap_KEY)
    );
    create table ComputationValues (
        id int8 not null,
        primary key (id)
    );
    create table ComputationValues_valueList (
        ComputationValues_id int8 not null,
        valueList float8,
        valueList_ORDER int4 not null,
        primary key (ComputationValues_id, valueList_ORDER)
    );
    create table District (
        id int8 not null,
        chamber varchar(255),
        district varchar(255),
        primary key (id)
    );
    create table District_AggregateValues (
        District_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (District_id, aggregateMap_KEY)
    );
    create table District_ComputationValues (
        District_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (District_id, computationMap_KEY)
    );
    create table District_Legislator (
        District_id int8 not null,
        legislators_id int8 not null
    );
    create table Districts (
        id int8 not null,
        primary key (id)
    );
    create table Districts_District (
        Districts_id int8 not null,
        districtList_id int8 not null
    );
    create table Districts_aggregateGroupMap (
        Districts_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY int8 not null,
        primary key (Districts_id, aggregateGroupMap_KEY)
    );
    create table Districts_computationGroupMap (
        Districts_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (Districts_id, computationGroupMap_KEY)
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
    create table GroupName (
        id int8 not null,
        groupName varchar(255),
        primary key (id)
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
        aggregateGroupMap_KEY int8 not null,
        primary key (Assembly_id, aggregateGroupMap_KEY)
    );
    create table assembly_computationgroupmap (
        Assembly_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (Assembly_id, computationGroupMap_KEY)
    );
    alter table Assembly_AggregateValues 
        add constraint UK_f6hs9i0i7ddkbe4tmfc8pbpyt unique (aggregateMap_id);
    alter table Assembly_ComputationValues 
        add constraint UK_3qxfbc503gp8iybc2vmovtsiq unique (computationMap_id);
    alter table District_AggregateValues 
        add constraint UK_rdnxu0p7owuxu6j0avevi9qh unique (aggregateMap_id);
    alter table District_ComputationValues 
        add constraint UK_dh0v5i1jqnpsiwk1paac77ifu unique (computationMap_id);
    alter table District_Legislator 
        add constraint UK_2uffod2ldeb2cv0yvsf750siv unique (legislators_id);
    alter table Districts_District 
        add constraint UK_fiygsso958eeod0puoi09e83n unique (districtList_id);
    alter table Districts_aggregateGroupMap 
        add constraint UK_7lcawj0i3iktaqyeac6f0tfsf unique (aggregateGroupMap_id);
    alter table Districts_computationGroupMap 
        add constraint UK_f37ermi5hxg38oej5ynafxfmb unique (computationGroupMap_id);
    alter table GroupName 
        add constraint UK_1a7nsttvx9t48v48rl7vfflcd unique (groupName);
    alter table assembly_aggregategroupmap 
        add constraint UK_9poknpmgasyyp1obdt5qoe5le unique (aggregateGroupMap_id);
    alter table assembly_computationgroupmap 
        add constraint UK_ob68eulskxrjds0u3vhe0u84b unique (computationGroupMap_id);
    alter table AggregateValues_valueList 
        add constraint FK_bgs8w1ud2yry8h1u3mmn515wg 
        foreign key (AggregateValues_id) 
        references AggregateValues;
    alter table Assembly 
        add constraint FK_l9wdewlweysuvf5unson90tht 
        foreign key (districts_id) 
        references Districts;
    alter table Assembly_AggregateValues 
        add constraint FK_f6hs9i0i7ddkbe4tmfc8pbpyt 
        foreign key (aggregateMap_id) 
        references AggregateValues;
    alter table Assembly_AggregateValues 
        add constraint FK_hx5h0m2wpbdp4t864c7mc1h1o 
        foreign key (aggregateMap_KEY) 
        references GroupName;
    alter table Assembly_AggregateValues 
        add constraint FK_1awpf56yeexc0k5btvgghcw2e 
        foreign key (Assembly_id) 
        references Assembly;
    alter table Assembly_ComputationValues 
        add constraint FK_3qxfbc503gp8iybc2vmovtsiq 
        foreign key (computationMap_id) 
        references ComputationValues;
    alter table Assembly_ComputationValues 
        add constraint FK_ktjl43oamuld6nsenmdf5wux5 
        foreign key (computationMap_KEY) 
        references GroupName;
    alter table Assembly_ComputationValues 
        add constraint FK_bv60ifrd86p80nbbuslom31mu 
        foreign key (Assembly_id) 
        references Assembly;
    alter table ComputationValues_valueList 
        add constraint FK_ptgvnduywhc12eqgjpivyghry 
        foreign key (ComputationValues_id) 
        references ComputationValues;
    alter table District_AggregateValues 
        add constraint FK_rdnxu0p7owuxu6j0avevi9qh 
        foreign key (aggregateMap_id) 
        references AggregateValues;
    alter table District_AggregateValues 
        add constraint FK_ercmlj4fu87653ejlb8ypdq1a 
        foreign key (aggregateMap_KEY) 
        references GroupName;
    alter table District_AggregateValues 
        add constraint FK_9r168cmwvjkdu9mtqjbdlxvw7 
        foreign key (District_id) 
        references District;
    alter table District_ComputationValues 
        add constraint FK_dh0v5i1jqnpsiwk1paac77ifu 
        foreign key (computationMap_id) 
        references ComputationValues;
    alter table District_ComputationValues 
        add constraint FK_aicbwbc0cdmc7eap2lbg4ixfd 
        foreign key (computationMap_KEY) 
        references GroupName;
    alter table District_ComputationValues 
        add constraint FK_gegsebt5ratwvqiufmlcx3wba 
        foreign key (District_id) 
        references District;
    alter table District_Legislator 
        add constraint FK_2uffod2ldeb2cv0yvsf750siv 
        foreign key (legislators_id) 
        references Legislator;
    alter table District_Legislator 
        add constraint FK_5pm9sjbqss6dnn64r4sraenc3 
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
    alter table Districts_aggregateGroupMap 
        add constraint FK_7lcawj0i3iktaqyeac6f0tfsf 
        foreign key (aggregateGroupMap_id) 
        references GroupInfo;
    alter table Districts_aggregateGroupMap 
        add constraint FK_7vcghng0872p1vun0uiysvp3s 
        foreign key (aggregateGroupMap_KEY) 
        references GroupName;
    alter table Districts_aggregateGroupMap 
        add constraint FK_4bv1rpdm0g7ijvy5qhasa00r 
        foreign key (Districts_id) 
        references Districts;
    alter table Districts_computationGroupMap 
        add constraint FK_f37ermi5hxg38oej5ynafxfmb 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table Districts_computationGroupMap 
        add constraint FK_okqck7l9lh1md36pdlnp2h6rp 
        foreign key (computationGroupMap_KEY) 
        references GroupName;
    alter table Districts_computationGroupMap 
        add constraint FK_81og4u2kc9qf1vg0bbwg4l0r6 
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
        add constraint FK_qqslcx0jhkp9ueo9faajjhwlr 
        foreign key (aggregateGroupMap_KEY) 
        references GroupName;
    alter table assembly_aggregategroupmap 
        add constraint FK_ajof2kgqyi3yprhc5c3v8ioxf 
        foreign key (Assembly_id) 
        references Assembly;
    alter table assembly_computationgroupmap 
        add constraint FK_ob68eulskxrjds0u3vhe0u84b 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table assembly_computationgroupmap 
        add constraint FK_a7betyowd17b66w8rjiq36ypv 
        foreign key (computationGroupMap_KEY) 
        references GroupName;
    alter table assembly_computationgroupmap 
        add constraint FK_9d4wqno205uf3pyai85yj7crt 
        foreign key (Assembly_id) 
        references Assembly;
    create sequence hibernate_sequence;