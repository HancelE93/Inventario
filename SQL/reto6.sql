select prov.cedula_p, prov.tipo_documento,td.descripcion ,prov.nombre, prov.telefono, prov.correo, prov.direccion
from proveedores prov,tipo_documento td
where prov.tipo_documento=td.codigo_tp
and upper(nombre) like '%SA%'

select cedula_p, tipo_documento ,nombre, telefono, correo, direccion
from proveedores
where upper(nombre) like '%SA%'

select codigo_tp,  descripcion
from tipo_documento


select prod.codigo_p, prod.nombre as nombre_producto, 
udm.codigo_u_m as nombre_umd
from producto prod,unidad_medida udm, categorias cat
where prod.codigo_u_m=udm.codigo_u_m
and prod.codigo_cat =cat.codigo_cat

select prod.codigo_p, prod.nombre as nombre_producto, 
udm.codigo_u_m as nombre_udm, udm.descripcion as descripcion_udm,
cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva,cast(prod.costo as decimal(5,4)),
prod.codigo_cat, cat.nombre as nombre_categoria, stock
from producto prod,unidad_medida udm, categorias cat
where prod.codigo_u_m=udm.codigo_u_m
and prod.codigo_cat =cat.codigo_cat
and upper(prod.nombre) like '%M%'