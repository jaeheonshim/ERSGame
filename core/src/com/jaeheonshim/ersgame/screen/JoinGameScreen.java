package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.UniversalOnscreenKeyboard;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.GameStateUpdateListener;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.JoinGamePacket;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextField;

public class JoinGameScreen implements Screen, GameStateUpdateListener {
    private final ERSTextField nameField;
    private final ERSTextField codeField;
    private final ERSTextButton joinButton;
    private final Button backButton;

    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    public JoinGameScreen(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

        table.padTop(32);

        backButton = new Button(skin, "back");

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainScreen);
            }
        });

        table.add(backButton).center().left().padLeft(32).padTop(4).height(64).width(74);

        ERSLabel titleLabel = new ERSLabel("Join Game", skin, "heading", game);
        table.add(titleLabel).expandX().center().left().padLeft(28);
        table.row();

        table.center();

        nameField = new ERSTextField("", skin, game);
        nameField.setMessageText("Username");
        nameField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.toLowerCase(c) >= 'a' && Character.toLowerCase(c) <= 'z' || Character.isDigit(c);
            }
        });

        table.add(nameField).expandY().fillX().padLeft(32).padRight(32).colspan(2).bottom();
        table.row();

        codeField = new ERSTextField("", skin, game);
        codeField.setMessageText("Join Code");
        codeField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

        table.add(codeField).fillX().padLeft(32).padRight(32).colspan(2);
        table.row();

        joinButton = new ERSTextButton("Join Game", skin, game);
        table.add(joinButton).fillX().expandY().top().padLeft(38).padRight(38).padTop(16).colspan(2);

        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(nameField.getText().isEmpty() || codeField.getText().isEmpty()) return;

                JoinGamePacket packet = new JoinGamePacket(codeField.getText(), nameField.getText());
                NetManager.getInstance().send(packet);
            }
        });

        GameStateManager.getInstance().registerListener(this);
    }

    @Override
    public void onUpdate(GameState newGameState) {
        if(!game.getScreen().equals(this)) return;

        if(newGameState != null) {
            game.setScreen(game.lobbyScreen);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
