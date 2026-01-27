-- CATEGORIAS
drop table if exists categorias;
create table categorias(
	codigo_cat serial not null,
	nombre varchar (100) not null,
	categoria_padre int ,
	constraint categorias_pk primary key (codigo_cat),
	constraint categorias_fk foreign key (categoria_padre)
	references categorias(codigo_cat)
);

insert into categorias (nombre,categoria_padre)
values ('Materia Prima',null);
insert into categorias (nombre,categoria_padre)
values ('Proteina',1);
insert into categorias (nombre,categoria_padre)
values ('Salsas',1);
insert into categorias (nombre,categoria_padre)
values ('Punto de venta',null);
insert into categorias (nombre,categoria_padre)
values ('Bebidas',4);
insert into categorias (nombre,categoria_padre)
values ('Con alcohol',5);
insert into categorias (nombre,categoria_padre)
values ('Sin alcohol',5);

select * from categorias;

--CATEGORIAS UNIDAD DE MEDIDA 
drop table if exists categorias_unidad_medida;
create table categorias_unidad_medida(
	codigo_cat_u_m char (1) not null,
	nombre varchar (100) not null,
	constraint categorias_unidad_medida_pk primary key (codigo_cat_u_m)
);

insert into categorias_unidad_medida (codigo_cat_u_m,nombre)
values ('U','Unidad');
insert into categorias_unidad_medida (codigo_cat_u_m,nombre)
values ('V','Volumen');
insert into categorias_unidad_medida (codigo_cat_u_m,nombre)
values ('P','Peso');

select * from categorias_unidad_medida;


-- UNIDAD DE MEDIDA 
drop table if exists unidad_medida;
create table unidad_medida(
	codigo_u_m char(2) not null,
	descripcion varchar(100) not null,
	codigo_cat_u_m char(1) not null,

	constraint unidad_medida_pk primary key (codigo_u_m),
	constraint unidad_medida_cat_fk foreign key (codigo_cat_u_m)
		references categorias_unidad_medida (codigo_cat_u_m)
);

insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('ml','mililitro','V');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('l','litro','V');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('u','unidad','U');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('d','docena','U');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('g','gramo','P');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('kg','kilogramo','P');
insert into unidad_medida (codigo_u_m,descripcion,codigo_cat_u_m)
values ('lb','libra','P');

select * from unidad_medida

--PRODUCTO

DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
    codigo_p serial NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    codigo_u_m CHAR(2) NOT NULL,
    precio_venta NUMERIC(10,4) NOT NULL,
    tiene_iva BOOLEAN NOT NULL,
    costo NUMERIC(10,4) NOT NULL,
    codigo_cat INT NOT NULL,
	stock int not null,

    CONSTRAINT producto_pk PRIMARY KEY (codigo_p),

    CONSTRAINT producto_um_fk FOREIGN KEY (codigo_u_m)
        REFERENCES unidad_medida (codigo_u_m),

    CONSTRAINT producto_categoria_fk FOREIGN KEY (codigo_cat)
        REFERENCES categorias (codigo_cat)
);


insert into producto (nombre, codigo_u_m,precio_venta,tiene_iva,costo,codigo_cat,stock)
values ('Coca cola pequeña','u',0.5804,'true',0.3723,7,100);
insert into producto (nombre, codigo_u_m,precio_venta,tiene_iva,costo,codigo_cat,stock)
values ('Salsa de tomate','kg',0.95,'true',0.8736,3,0);
insert into producto (nombre, codigo_u_m,precio_venta,tiene_iva,costo,codigo_cat,stock)
values ('Mostaza','kg',0.95,'true',0.89,3,0);
insert into producto (nombre, codigo_u_m,precio_venta,tiene_iva,costo,codigo_cat,stock)
values ('Fius tea','u',0.8,'true',0.7,7,49);

select * from producto;

--TIPO DE DOCUMENTO

DROP TABLE IF EXISTS tipo_documento;

CREATE TABLE tipo_documento (
    codigo_tp char(1) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
	
    CONSTRAINT tipo_documento_pk PRIMARY KEY ( codigo_tp)
);

insert into tipo_documento (codigo_tp,  descripcion)
values ('C','CEDULA');
insert into tipo_documento (codigo_tp,  descripcion)
values ('R','RUC');

select * from tipo_documento;

--PROVEEDORES
DROP TABLE IF EXISTS proveedores;
CREATE TABLE proveedores (
   	cedula_p VARCHAR(15) PRIMARY KEY,
    tipo_documento CHAR(1) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion VARCHAR(100),

    CONSTRAINT proveedores_fk FOREIGN KEY (tipo_documento)
        REFERENCES tipo_documento (codigo_tp)
);


insert into proveedores (cedula_p, tipo_documento ,nombre, telefono, correo, direccion)
values ('1792285747001','C','SANTIAGO MOSQUERA','0992920306','zantycb89@gmail.com','Cumbayor');
insert into proveedores (cedula_p, tipo_documento ,nombre, telefono, correo, direccion)
values ('1792285747002','R','SNACKS SA','0992320398','snack@gmail.com','La Tola');


select * from proveedores;

--ESTADO DE PEDIDOS
DROP TABLE IF EXISTS estado_pedidos;

CREATE TABLE estado_pedidos (
    codigo_ep char(1) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
	
    CONSTRAINT estado_pedidos_pk PRIMARY KEY ( codigo_ep)
);

insert into estado_pedidos (codigo_ep,  descripcion)
values ('S','SOLICITADO');
insert into estado_pedidos (codigo_ep,  descripcion)
values ('R','RECIBIDO');

select * from estado_pedidos ;

--CACECERA PEDIDIOS

DROP TABLE IF EXISTS cabecera_pedido;

CREATE TABLE cabecera_pedido (
    numero SERIAL PRIMARY KEY,
    proveedor VARCHAR(15) NOT NULL,
    fecha TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    estado CHAR(1) NOT NULL,

    CONSTRAINT cabecera_pedido_proveedor_fk
        FOREIGN KEY (proveedor)
        REFERENCES proveedores (cedula_p),

    CONSTRAINT cabecera_pedido_estado_fk
        FOREIGN KEY (estado)
        REFERENCES estado_pedidos (codigo_ep)
);

INSERT INTO cabecera_pedido (proveedor, fecha, estado)
VALUES ('1792285747001', '2023-11-20 00:00:00', 'R');

INSERT INTO cabecera_pedido (proveedor, fecha, estado)
VALUES ('1792285747002', '2023-11-20 00:00:00', 'S');

select * from cabecera_pedido;


-- DETALLES DE PEDIDO
DROP TABLE IF EXISTS detalle_pedido;

CREATE TABLE detalle_pedido (
    codigo_dp SERIAL NOT NULL,
    numero_pedido INT NOT NULL,
    codigo_producto INT NOT NULL,
    cantidad_solicitada INT NOT NULL,
    subtotal NUMERIC(10,4) NOT NULL,
    cantidad_recibida INT NOT NULL,

    CONSTRAINT detalle_pedido_pk PRIMARY KEY (codigo_dp),

    CONSTRAINT detalle_pedido_cabecera_fk
        FOREIGN KEY (numero_pedido)
        REFERENCES cabecera_pedido (numero),

    CONSTRAINT detalle_pedido_producto_fk
        FOREIGN KEY (codigo_producto)
        REFERENCES producto (codigo_p)
);

INSERT INTO detalle_pedido
(numero_pedido, codigo_producto, cantidad_solicitada, subtotal, cantidad_recibida)
VALUES (1, 1, 100, 37.29, 100);

INSERT INTO detalle_pedido
(numero_pedido, codigo_producto, cantidad_solicitada, subtotal, cantidad_recibida)
VALUES (1, 4, 50, 11.80, 50);

INSERT INTO detalle_pedido
(numero_pedido, codigo_producto, cantidad_solicitada, subtotal, cantidad_recibida)
VALUES (2, 1, 10, 3.73, 0);

select * from detalle_pedido;


--HISTORIAL DE STOCK

DROP TABLE IF EXISTS historial_stock;

CREATE TABLE historial_stock (
    codigo_hs SERIAL NOT NULL,
    fecha TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    referencia VARCHAR(50) NOT NULL,
    codigo_producto INT NOT NULL,
    cantidad INT NOT NULL,

    CONSTRAINT historial_stock_pk PRIMARY KEY (codigo_hs),

    CONSTRAINT historial_stock_producto_fk
        FOREIGN KEY (codigo_producto)
        REFERENCES producto (codigo_p)
);
INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad)
VALUES ('2023-11-20 00:00:00', 'PEDIDO 1', 1, 100);

INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad)
VALUES ('2023-11-20 00:00:00', 'PEDIDO 1', 4, 50);

INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad)
VALUES ('2023-11-20 00:00:00', 'PEDIDO 2', 1, 10);

INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad)
VALUES ('2023-11-20 00:00:00', 'VENTA 1', 1, -5);

INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad)
VALUES ('2023-11-20 00:00:00', 'VENTA 1', 4, -1);

select * from historial_stock; 

--CABECERA VENTAS

DROP TABLE IF EXISTS cabecera_ventas;

CREATE TABLE cabecera_ventas (
    codigo_cv SERIAL NOT NULL,
    fecha TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    total_sin_iva NUMERIC(10,4) NOT NULL,
    iva NUMERIC(10,4) NOT NULL,
    total NUMERIC(10,4) NOT NULL,

    CONSTRAINT cabecera_ventas_pk PRIMARY KEY (codigo_cv)
);
INSERT INTO cabecera_ventas (fecha, total_sin_iva, iva, total)
VALUES ('2023-11-20 00:00:00', 3.26, 0.39, 3.65);

SELECT * FROM cabecera_ventas;


--DETALLE DE VENTAS 

DROP TABLE IF EXISTS detalle_ventas;

CREATE TABLE detalle_ventas (
    codigo_dv SERIAL NOT NULL,
    codigo_cv INT NOT NULL,
    codigo_p INT NOT NULL,
    cantidad INT NOT NULL,
    precio_venta NUMERIC(10,4) NOT NULL,
    subtotal NUMERIC(10,4) NOT NULL,
    subtotal_iva NUMERIC(10,4) NOT NULL,

    CONSTRAINT detalle_ventas_pk PRIMARY KEY (codigo_dv),

    CONSTRAINT detalle_ventas_cv_fk FOREIGN KEY (codigo_cv)
        REFERENCES cabecera_ventas (codigo_cv),

    CONSTRAINT detalle_ventas_producto_fk FOREIGN KEY (codigo_p)
        REFERENCES producto (codigo_p)
);
INSERT INTO detalle_ventas 
(codigo_cv, codigo_p, cantidad, precio_venta, subtotal, subtotal_iva)
VALUES
(1, 1, 5, 0.58, 2.90, 3.25),
(1, 4, 1, 0.36, 0.36, 0.40);

SELECT * FROM detalle_ventas;
