package at.grueneis.spengergasse.registry;

/**
 * Created by gradnig on 19.02.14.
 */
public class EFAttributeTestClassTwo implements EFPersistable{
    private String m_String;
    private Long m_Id;
    private EFAttributeTestClass m_Child;

    @Override
    public Long getId() {
        return m_Id;
    }

    public EFAttributeTestClassTwo(long id) {
        m_Id = id;
    }

    @EFAttribute
    public int getInt() {
        return 13;
    }

    @EFAttribute
    public String getString() {
        return m_String;
    }

    public String getNoEFAttribute() {
        return "Nope";
    }

    public void setString(String string) {
        m_String = string;
    }

    public void setChild(EFAttributeTestClass child) {
        m_Child = child;
    }

    @EFReference
    public EFAttributeTestClass getChild(){
        return m_Child;
    }
}

