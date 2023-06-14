package CLASES;

public class Laboratorio implements Estudiante {
	
	private int DNI;
	private String name, genero, curso, tipo;
	double t1, t2, ep, ef, nf;

	public Laboratorio(int dNI, String name, String genero, String curso, double t1, double t2, double ep,
			double ef, double nf) {
		DNI = dNI;
		this.name = name;
		this.genero = genero;
		this.curso = curso;
		this.tipo = "Laboratorio";
		this.t1 = t1;
		this.t2 = t2;
		this.ep = ep;
		this.ef = ef;
		this.nf = nf;
	}

	public int txtDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public double getT1() {
		return t1;
	}

	public void setT1(double t1) {
		this.t1 = t1;
	}

	public double getT2() {
		return t2;
	}

	public void setT2(double t2) {
		this.t2 = t2;
	}

	public double getEp() {
		return ep;
	}

	public void setEp(double ep) {
		this.ep = ep;
	}

	public double getEf() {
		return ef;
	}

	public void setEf(double ef) {
		this.ef = ef;
	}

	public double getNf() {
		return nf;
	}

	public void setNf(double nf) {
		this.nf = nf;
	}

	public String getTipo() {
		return "Laboratorio";
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}

