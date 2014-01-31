package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 1/31/14
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class EFAttributeTestClass extends BasePersistable
{
    private String m_String;

    public EFAttributeTestClass()
    {

    }

    @EFAttribute
    public int getInt()
    {
        return 25;
    }

    @EFAttribute
    public String getString()
    {
        return m_String;
    }

    public String getNoEFAttribute()
    {
        return "Nope";
    }

    public void setString(String string)
    {
        m_String = string;
    }
}
