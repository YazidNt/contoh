package koneksi;

public class TampungData {
	static String kode;
	static String nama = "";
	static String alamat = "";
	static String foto="";
	static double latitude;
	static double longitude;
	static String sejarah = "";
	static String biaya = "";
	static String fasilitas = "";
	static String [] wahana;

	public TampungData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the kode
	 */
	public static String getKode() {
		return kode;
	}

	/**
	 * @param kode the kode to set
	 */
	public static void setKode(String kode) {
		TampungData.kode = kode;
	}

	/**
	 * @return the nama
	 */
	public static String getNama() {
		return nama;
	}

	/**
	 * @param nama the nama to set
	 */
	public static void setNama(String nama) {
		TampungData.nama = nama;
	}

	/**
	 * @return the alamat
	 */
	public static String getAlamat() {
		return alamat;
	}

	/**
	 * @param alamat the alamat to set
	 */
	public static void setAlamat(String alamat) {
		TampungData.alamat = alamat;
	}

	/**
	 * @return the foto
	 */
	public static String getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public static void setFoto(String foto) {
		TampungData.foto = foto;
	}

	/**
	 * @return the latitude
	 */
	public static double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public static void setLatitude(double latitude) {
		TampungData.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public static double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public static void setLongitude(double longitude) {
		TampungData.longitude = longitude;
	}

	/**
	 * @return the sejarah
	 */
	public static String getSejarah() {
		return sejarah;
	}

	/**
	 * @param sejarah the sejarah to set
	 */
	public static void setSejarah(String sejarah) {
		TampungData.sejarah = sejarah;
	}

	/**
	 * @return the biaya
	 */
	public static String getBiaya() {
		return biaya;
	}

	/**
	 * @param biaya the biaya to set
	 */
	public static void setBiaya(String biaya) {
		TampungData.biaya = biaya;
	}

	/**
	 * @return the fasilitas
	 */
	public static String getFasilitas() {
		return fasilitas;
	}

	/**
	 * @param fasilitas the fasilitas to set
	 */
	public static void setFasilitas(String fasilitas) {
		TampungData.fasilitas = fasilitas;
	}

	/**
	 * @return the wahana
	 */
	public static String[] getWahana() {
		return wahana;
	}

	/**
	 * @param wahana the wahana to set
	 */
	public static void setWahana(String[] wahana) {
		TampungData.wahana = wahana;
	}

}
