import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class Nastroj {
    protected int vaha;
    protected String nazev;

    public Nastroj(int vaha, String nazev) {
        this.vaha = vaha;
        this.nazev = nazev;
    }

    public abstract String pracuj();
}

class Pila extends Nastroj {
    public Pila(int vaha, String nazev) {
        super(vaha, nazev);
    }

    @Override
    public String pracuj() {
        return "Řežu";
    }
}

class Sroubovak extends Nastroj {
    public Sroubovak(int vaha, String nazev) {
        super(vaha, nazev);
    }

    @Override
    public String pracuj() {
        return "Šroubuji";
    }
}

class Kladivo extends Nastroj {
    private boolean obourucni;

    public Kladivo(int vaha, String nazev, boolean obourucni) {
        super(vaha, nazev);
        this.obourucni = obourucni;
    }

    @Override
    public String pracuj() {
        return "Zatloukám";
    }

    public boolean isObourucni() {
        return obourucni;
    }
}

class ElektrickySroubovak extends Sroubovak {
    private int kapacitaBaterie;

    public ElektrickySroubovak(int vaha, String nazev, int kapacitaBaterie) {
        super(vaha, nazev);
        this.kapacitaBaterie = kapacitaBaterie;
    }

    @Override
    public String pracuj() {
        return "Šroubuji elektricky s kapacitou " + kapacitaBaterie;
    }
}

class Bedna {
    private int nosnost;
    private int aktualniVaha;
    private List<Nastroj> nastroje;

    public Bedna(int nosnost) {
        this.nosnost = nosnost;
        this.aktualniVaha = 0;
        this.nastroje = new ArrayList<>();
    }

    public boolean vlozNastroj(Nastroj nastroj) {
        if (aktualniVaha + nastroj.vaha <= nosnost) {
            nastroje.add(nastroj);
            aktualniVaha += nastroj.vaha;
            return true;
        }
        return false;
    }

    public boolean vyndejNastroj(Nastroj nastroj) {
        if (nastroje.remove(nastroj)) {
            aktualniVaha -= nastroj.vaha;
            return true;
        }
        return false;
    }

    public int celkovaHmotnostObourucnichKladi() {
        int hmotnost = 0;
        for (Nastroj nastroj : nastroje) {
            if (nastroj instanceof Kladivo && ((Kladivo) nastroj).isObourucni()) {
                hmotnost += nastroj.vaha;
            }
        }
        return hmotnost;
    }

    public List<Nastroj> getNastroje() {
        return Collections.unmodifiableList(nastroje);
    }
}

public class NastrojeIndex {
    public static void main(String[] args) {
        Bedna bedna = new Bedna(10000);

        Pila zrezivelaPila = new Pila(2000, "Zrezivělá pila");
        Kladivo kladivko = new Kladivo(1000, "Kladívko", false);
        Kladivo velkeKladivo = new Kladivo(3000, "Velké kladivo", true);
        Kladivo bouraciKladivo = new Kladivo(4000, "Bourací kladivo", true);
        Sroubovak sroubovakPhilips = new Sroubovak(500, "Šroubovák Philips");
        ElektrickySroubovak elektrickySroubovakBosh = new ElektrickySroubovak(1000, "Elektrický šroubovák Bosh", 4000);

        bedna.vlozNastroj(kladivko);
        bedna.vlozNastroj(velkeKladivo);
        bedna.vlozNastroj(bouraciKladivo);
        bedna.vlozNastroj(sroubovakPhilips);
        bedna.vlozNastroj(zrezivelaPila);
        bedna.vlozNastroj(elektrickySroubovakBosh);

        System.out.println("V bedně je: ");
        for (Nastroj nastroj : bedna.getNastroje()) {
            System.out.println(nastroj.nazev);
        }

        System.out.println("\nVyndavám nástroje:");
        System.out.println("Zrezivělá pila: " + (bedna.vyndejNastroj(zrezivelaPila) ? "vyndáno" : "není v bedně"));
        System.out.println("Kladívko: " + (bedna.vyndejNastroj(kladivko) ? "vyndáno" : "není v bedně"));
        System.out.println("Šroubovák Philips: " + (bedna.vyndejNastroj(sroubovakPhilips) ? "vyndáno" : "není v bedně"));

        System.out.println("\nV bedně je: ");
        for (Nastroj nastroj : bedna.getNastroje()) {
            System.out.println(nastroj.nazev);
        }

        int hmotnostObourucnichKladi = bedna.celkovaHmotnostObourucnichKladi();
        System.out.println("\nVáha obouručních kladiv je " + hmotnostObourucnichKladi + " gramů");

        System.out.println();
        for (Nastroj nastroj : bedna.getNastroje()) {
            System.out.println(nastroj.pracuj());
        }
    }
}
