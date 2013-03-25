import models.vo.BIRCH;
import models.vo.TreeNode;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        BIRCH birch = new BIRCH();
        TreeNode root = birch.buildBTree();
        birch.printTree(root);
    }

}
