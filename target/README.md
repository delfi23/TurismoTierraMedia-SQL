# Turismo En La Tierra Media
Sistema que ofrece paquetes turísticos al usuario teniendo en cuenta sus preferencias, su presupuesto y el tiempo que dispone. Se crea luego un itinerario con las opciones que elige.

## Contenido
  1. El sistema lee la base de datos de entrada que contiene:
      - Usuarios con sus preferencias
      - Atracciones con sus características
      - Promociones a ofrecer
  2. Para cada usuario, el sistema:
      - Sugiere una atracción que coincida con sus preferencias, costos y tiempos. Teniendo en cuenta lo siguiente:
          - Se prioriza la oferta de promociones, las atracciones más caras y que requieran mayor tiempo, en ese orden.
          - No oferta una atracción o promoción que no pueda costearse o para la cual no tenga tiempo disponible.
          - Tampoco oferta una atracción incluida en una promoción anteriormente comprada por el mismo usuario.
          - Una vez agotadas las ofertas que coincidan con sus intereses, se ofertan aquellas que no coincidan, bajo el mismo criterio.
      - Lo que el usuario acepta/compra, se guarda dentro de su itinerario. Una atracción o promoción aceptada no podrá cancelarse.
      - Se repite el proceso hasta que no quede tiempo disponible, monedas, o cupo en las atracciones, conforme las ofertas restantes.
      - Se guarda un resumen de cada una de sus compras en la base de datos.
      - Se repite para el siguiente usuario.
      - La interacción se realiza por medio de la línea de comandos.
  3. Cada compra realizada queda guardada en la base de datos en la tabla itinerarios.
