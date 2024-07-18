package cn.learn.home;

public class Wife implements Mugou {

    private Husband husband;
    private IMother mother; // 婆婆

    @Override
    public String queryHusband() {
        return "Wife.husband、Mother.callMother：" + mother.callMother();
    }

}