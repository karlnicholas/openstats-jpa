
    alter table AggregateValues_valueList 
        drop constraint FK_bgs8w1ud2yry8h1u3mmn515wg;
    alter table ComputationValues_valueList 
        drop constraint FK_ptgvnduywhc12eqgjpivyghry;
    alter table DBAssembly 
        drop constraint FK_d831tqk4156qmn972x3awocod;
    alter table DBAssembly_AggregateValues 
        drop constraint FK_3a7dhcfqcohp0ekisruas2cjn;
    alter table DBAssembly_AggregateValues 
        drop constraint FK_qy4hbcebe4bnek0jv2j306vk7;
    alter table DBAssembly_AggregateValues 
        drop constraint FK_3frcwi9rnalimawlnjx6evdkj;
    alter table DBAssembly_ComputationValues 
        drop constraint FK_s1lkv43eai929j0xpis2c9wie;
    alter table DBAssembly_ComputationValues 
        drop constraint FK_2kten7r4dnd8hohd1kpht5jn1;
    alter table DBAssembly_ComputationValues 
        drop constraint FK_ih8s3i0xcvsc9y2kw8f1hc4o1;
    alter table DBDistrict_AggregateValues 
        drop constraint FK_70e84lpj56by1to85mjpeuytn;
    alter table DBDistrict_AggregateValues 
        drop constraint FK_egds07027fn14jg2iml6jf4ll;
    alter table DBDistrict_AggregateValues 
        drop constraint FK_sqnx3xnyjtapji8rs8pqq6jjd;
    alter table DBDistrict_ComputationValues 
        drop constraint FK_m7dib6v28rut51c2mjgcec1md;
    alter table DBDistrict_ComputationValues 
        drop constraint FK_fnx8d24bo33y5jr825393n573;
    alter table DBDistrict_ComputationValues 
        drop constraint FK_76l2dxxayxpx94vjgr9y4d05o;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_neyks3k879m3g9wuqutimjdce;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_9xpiwbyuy1xjprewodxsf88cc;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_2uiahvgobqsuby3loxlaaw7yb;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_r81r07dube1quemr7r7nrgs7o;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_8591nauyvlo84o69e4af265wh;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_kwjleaqh3r5n2wpdv10ln3sbx;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_2vht55myh4li65ppwblhk0rc2;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_j4hurmg0j2bu09y48uy4jmbik;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_438t43u5g59cwbut6fxohjm1r;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_epe6ngy0pw6dqbfbx0gj9f2nd;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_gk2n11lfx77klw8ndyxy4mg8j;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_e9e3y0koemwc0yg16s1f1xpkw;
    alter table assembly_aggregategroupmap 
        drop constraint FK_9poknpmgasyyp1obdt5qoe5le;
    alter table assembly_aggregategroupmap 
        drop constraint FK_qqslcx0jhkp9ueo9faajjhwlr;
    alter table assembly_aggregategroupmap 
        drop constraint FK_8p8bot46e5uka0bxr9cg5181s;
    alter table assembly_computationgroupmap 
        drop constraint FK_ob68eulskxrjds0u3vhe0u84b;
    alter table assembly_computationgroupmap 
        drop constraint FK_a7betyowd17b66w8rjiq36ypv;
    alter table assembly_computationgroupmap 
        drop constraint FK_phl554wenadqr28f1y8yhksp3;
    drop table if exists AggregateValues cascade;
    drop table if exists AggregateValues_valueList cascade;
    drop table if exists ComputationValues cascade;
    drop table if exists ComputationValues_valueList cascade;
    drop table if exists DBAssembly cascade;
    drop table if exists DBAssembly_AggregateValues cascade;
    drop table if exists DBAssembly_ComputationValues cascade;
    drop table if exists DBDistrict cascade;
    drop table if exists DBDistrict_AggregateValues cascade;
    drop table if exists DBDistrict_ComputationValues cascade;
    drop table if exists DBDistrict_DBLegislator cascade;
    drop table if exists DBDistricts cascade;
    drop table if exists DBDistricts_DBDistrict cascade;
    drop table if exists DBDistricts_aggregateGroupMap cascade;
    drop table if exists DBDistricts_computationGroupMap cascade;
    drop table if exists DBGroup cascade;
    drop table if exists DBGroupInfo cascade;
    drop table if exists DBGroupInfo_DBInfoItem cascade;
    drop table if exists DBInfoItem cascade;
    drop table if exists DBLegislator cascade;
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
    create table DBAssembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
    );
    create table DBAssembly_AggregateValues (
        DBAssembly_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (DBAssembly_id, aggregateMap_KEY)
    );
    create table DBAssembly_ComputationValues (
        DBAssembly_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (DBAssembly_id, computationMap_KEY)
    );
    create table DBDistrict (
        id int8 not null,
        chamber int4,
        description varchar(255),
        district varchar(3),
        name varchar(255),
        primary key (id)
    );
    create table DBDistrict_AggregateValues (
        DBDistrict_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (DBDistrict_id, aggregateMap_KEY)
    );
    create table DBDistrict_ComputationValues (
        DBDistrict_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (DBDistrict_id, computationMap_KEY)
    );
    create table DBDistrict_DBLegislator (
        DBDistrict_id int8 not null,
        legislators_id int8 not null
    );
    create table DBDistricts (
        id int8 not null,
        primary key (id)
    );
    create table DBDistricts_DBDistrict (
        DBDistricts_id int8 not null,
        districtList_id int8 not null
    );
    create table DBDistricts_aggregateGroupMap (
        DBDistricts_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY int8 not null,
        primary key (DBDistricts_id, aggregateGroupMap_KEY)
    );
    create table DBDistricts_computationGroupMap (
        DBDistricts_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (DBDistricts_id, computationGroupMap_KEY)
    );
    create table DBGroup (
        id int8 not null,
        groupDescription varchar(255),
        groupName varchar(255),
        primary key (id)
    );
    create table DBGroupInfo (
        id int8 not null,
        primary key (id)
    );
    create table DBGroupInfo_DBInfoItem (
        DBGroupInfo_id int8 not null,
        groupItems_id int8 not null,
        groupItems_ORDER int4 not null,
        primary key (DBGroupInfo_id, groupItems_ORDER)
    );
    create table DBInfoItem (
        id int8 not null,
        Label varchar(255),
        description varchar(1023),
        primary key (id)
    );
    create table DBLegislator (
        id int8 not null,
        name varchar(255),
        party varchar(255),
        primary key (id)
    );
    create table assembly_aggregategroupmap (
        DBAssembly_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY int8 not null,
        primary key (DBAssembly_id, aggregateGroupMap_KEY)
    );
    create table assembly_computationgroupmap (
        DBAssembly_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (DBAssembly_id, computationGroupMap_KEY)
    );
    alter table DBAssembly_AggregateValues 
        add constraint UK_3a7dhcfqcohp0ekisruas2cjn  unique (aggregateMap_id);
    alter table DBAssembly_ComputationValues 
        add constraint UK_s1lkv43eai929j0xpis2c9wie  unique (computationMap_id);
    alter table DBDistrict_AggregateValues 
        add constraint UK_70e84lpj56by1to85mjpeuytn  unique (aggregateMap_id);
    alter table DBDistrict_ComputationValues 
        add constraint UK_m7dib6v28rut51c2mjgcec1md  unique (computationMap_id);
    alter table DBDistrict_DBLegislator 
        add constraint UK_neyks3k879m3g9wuqutimjdce  unique (legislators_id);
    alter table DBDistricts_DBDistrict 
        add constraint UK_2uiahvgobqsuby3loxlaaw7yb  unique (districtList_id);
    alter table DBDistricts_aggregateGroupMap 
        add constraint UK_8591nauyvlo84o69e4af265wh  unique (aggregateGroupMap_id);
    alter table DBDistricts_computationGroupMap 
        add constraint UK_j4hurmg0j2bu09y48uy4jmbik  unique (computationGroupMap_id);
    alter table DBGroup 
        add constraint UK_fnj4ivbsm7v9e9b4q29g7k9vk  unique (groupName);
    alter table DBGroupInfo_DBInfoItem 
        add constraint UK_gk2n11lfx77klw8ndyxy4mg8j  unique (groupItems_id);
    alter table assembly_aggregategroupmap 
        add constraint UK_9poknpmgasyyp1obdt5qoe5le  unique (aggregateGroupMap_id);
    alter table assembly_computationgroupmap 
        add constraint UK_ob68eulskxrjds0u3vhe0u84b  unique (computationGroupMap_id);
    alter table AggregateValues_valueList 
        add constraint FK_bgs8w1ud2yry8h1u3mmn515wg 
        foreign key (AggregateValues_id) 
        references AggregateValues;
    alter table ComputationValues_valueList 
        add constraint FK_ptgvnduywhc12eqgjpivyghry 
        foreign key (ComputationValues_id) 
        references ComputationValues;
    alter table DBAssembly 
        add constraint FK_d831tqk4156qmn972x3awocod 
        foreign key (districts_id) 
        references DBDistricts;
    alter table DBAssembly_AggregateValues 
        add constraint FK_3a7dhcfqcohp0ekisruas2cjn 
        foreign key (aggregateMap_id) 
        references AggregateValues;
    alter table DBAssembly_AggregateValues 
        add constraint FK_qy4hbcebe4bnek0jv2j306vk7 
        foreign key (aggregateMap_KEY) 
        references DBGroup;
    alter table DBAssembly_AggregateValues 
        add constraint FK_3frcwi9rnalimawlnjx6evdkj 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table DBAssembly_ComputationValues 
        add constraint FK_s1lkv43eai929j0xpis2c9wie 
        foreign key (computationMap_id) 
        references ComputationValues;
    alter table DBAssembly_ComputationValues 
        add constraint FK_2kten7r4dnd8hohd1kpht5jn1 
        foreign key (computationMap_KEY) 
        references DBGroup;
    alter table DBAssembly_ComputationValues 
        add constraint FK_ih8s3i0xcvsc9y2kw8f1hc4o1 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table DBDistrict_AggregateValues 
        add constraint FK_70e84lpj56by1to85mjpeuytn 
        foreign key (aggregateMap_id) 
        references AggregateValues;
    alter table DBDistrict_AggregateValues 
        add constraint FK_egds07027fn14jg2iml6jf4ll 
        foreign key (aggregateMap_KEY) 
        references DBGroup;
    alter table DBDistrict_AggregateValues 
        add constraint FK_sqnx3xnyjtapji8rs8pqq6jjd 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistrict_ComputationValues 
        add constraint FK_m7dib6v28rut51c2mjgcec1md 
        foreign key (computationMap_id) 
        references ComputationValues;
    alter table DBDistrict_ComputationValues 
        add constraint FK_fnx8d24bo33y5jr825393n573 
        foreign key (computationMap_KEY) 
        references DBGroup;
    alter table DBDistrict_ComputationValues 
        add constraint FK_76l2dxxayxpx94vjgr9y4d05o 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistrict_DBLegislator 
        add constraint FK_neyks3k879m3g9wuqutimjdce 
        foreign key (legislators_id) 
        references DBLegislator;
    alter table DBDistrict_DBLegislator 
        add constraint FK_9xpiwbyuy1xjprewodxsf88cc 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistricts_DBDistrict 
        add constraint FK_2uiahvgobqsuby3loxlaaw7yb 
        foreign key (districtList_id) 
        references DBDistrict;
    alter table DBDistricts_DBDistrict 
        add constraint FK_r81r07dube1quemr7r7nrgs7o 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_8591nauyvlo84o69e4af265wh 
        foreign key (aggregateGroupMap_id) 
        references DBGroupInfo;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_kwjleaqh3r5n2wpdv10ln3sbx 
        foreign key (aggregateGroupMap_KEY) 
        references DBGroup;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_2vht55myh4li65ppwblhk0rc2 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_j4hurmg0j2bu09y48uy4jmbik 
        foreign key (computationGroupMap_id) 
        references DBGroupInfo;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_438t43u5g59cwbut6fxohjm1r 
        foreign key (computationGroupMap_KEY) 
        references DBGroup;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_epe6ngy0pw6dqbfbx0gj9f2nd 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_gk2n11lfx77klw8ndyxy4mg8j 
        foreign key (groupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_e9e3y0koemwc0yg16s1f1xpkw 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_9poknpmgasyyp1obdt5qoe5le 
        foreign key (aggregateGroupMap_id) 
        references DBGroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_qqslcx0jhkp9ueo9faajjhwlr 
        foreign key (aggregateGroupMap_KEY) 
        references DBGroup;
    alter table assembly_aggregategroupmap 
        add constraint FK_8p8bot46e5uka0bxr9cg5181s 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table assembly_computationgroupmap 
        add constraint FK_ob68eulskxrjds0u3vhe0u84b 
        foreign key (computationGroupMap_id) 
        references DBGroupInfo;
    alter table assembly_computationgroupmap 
        add constraint FK_a7betyowd17b66w8rjiq36ypv 
        foreign key (computationGroupMap_KEY) 
        references DBGroup;
    alter table assembly_computationgroupmap 
        add constraint FK_phl554wenadqr28f1y8yhksp3 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    create sequence hibernate_sequence;