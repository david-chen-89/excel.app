package shipment.report;

public class Util {

	private Util() {
	}

	/**
	 * 如果有多个reference在一起的话，默认显示前三个
	 */
	public static String chgReference(String ref) {
		String[] refs = ref.split(",");
		if (refs.length < 4) {
			return ref;
		} else
			return refs[0] + "," + refs[1] + "," + refs[2];
	}

	/**
	 * 客人留言，希望将里面的 ' PXXXX message from XXXXX: '这句话都删掉
	 */
	public static String chgNote(String note) {
		return note.replaceAll("[Pp].+\\s+message\\s+from\\s+.+:\\s{0,1}", "");
	}
}
