create sequence hibernate_sequence start 1 increment 1

    create table cards (
       card_id int8 not null,
        attack int4 not null,
        card_class varchar(255),
        mana_cost int4,
        dust_cost int4,
        health int4 not null,
        id_string varchar(255) not null,
        name varchar(255),
        rarity varchar(255),
        card_set varchar(255),
        text varchar(255),
        card_type varchar(255),
        primary key (card_id)
    )

    create table cards_decks (
       cards_card_id int8 not null,
        deck_id int8 not null
    )

    create table decks (
       id  bigserial not null,
        name varchar(255),
        user_id int8,
        primary key (id)
    )

    create table users (
       id  bigserial not null,
        email varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    )

    alter table cards 
       add constraint UK_jtgiuqx2n80ql2sx83jlpk1t6 unique (id_string)

    alter table users 
       add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)

    alter table cards_decks 
       add constraint FK7chqif4raab6lny3y3w5kcf7w 
       foreign key (deck_id) 
       references decks

    alter table cards_decks 
       add constraint FKnvkmpkdvmrm4ed1cvxdlb63d2 
       foreign key (cards_card_id) 
       references cards

    alter table decks 
       add constraint FKj0ey511pphfxbxbh8ri1616uv 
       foreign key (user_id) 
       references users
