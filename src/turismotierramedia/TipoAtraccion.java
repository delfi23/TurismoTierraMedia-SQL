package turismotierramedia;

public enum TipoAtraccion {
	AVENTURA, BELICA, PAISAJE, DEGUSTACION;

	public int toInt() {

		int valor = 0;

		if (this == AVENTURA)
			valor = 1;
		if (this == BELICA)
			valor = 2;
		if (this == DEGUSTACION)
			valor = 3;
		if (this == PAISAJE)
			valor = 4;

		return valor;
	}
}
