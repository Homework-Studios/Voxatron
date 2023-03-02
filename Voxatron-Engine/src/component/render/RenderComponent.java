package component.render;

import com.raylib.java.Raylib;
import component.Component;

public abstract class RenderComponent extends Component {

        public abstract void render(Raylib raylib);

}
