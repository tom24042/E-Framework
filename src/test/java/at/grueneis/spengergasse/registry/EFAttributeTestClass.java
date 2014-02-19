package at.grueneis.spengergasse.registry;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 1/31/14
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class EFAttributeTestClass implements EFPersistable {
    private String m_String;
    private Long m_Id;
    private EFAttributeTestClass m_Child;

    @Override
    public Long getId() {
        return m_Id;
    }

    public EFAttributeTestClass(long id) {
        m_Id = id;
    }

    @EFAttribute
    public int getInt() {
        return 25;
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
