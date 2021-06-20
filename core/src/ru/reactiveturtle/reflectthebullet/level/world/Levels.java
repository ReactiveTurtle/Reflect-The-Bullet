package ru.reactiveturtle.reflectthebullet.level.world;

public final class Levels {
    private Levels() {
    }

    /*public static class Level_desert_open1 implements Level {
        @Override
        public void loadTo(Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            keeper.setScoreTableParams(Color.GOLDENROD, Gdx.graphics.getHeight());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("desert_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);

            List<StaticObject> staticObjects = new ArrayList<StaticObject>();

            float blockWidth = width() / 12f;
            float blockHeight = (float) height() / (int) (height() / blockWidth);

            Texture texture = new Texture(Gdx.files.internal("target.png"));
            TrainTarget target = new TrainTarget(width() / 24f, width() / 4f, texture);
            target.setPosition(width() - target.getWidth(), height() - target.getHeight());
            target.createBody(world);
            staticObjects.add(target);

            keeper.putStaticObjects(staticObjects);
            keeper.putContactListener(getContactListener(keeper));
        }
    }

    public static class Level_desert_open2 implements Level {
        @Override
        public void loadTo(Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            keeper.setScoreTableParams(Color.GOLDENROD, Gdx.graphics.getHeight());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("desert_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);

            List<StaticObject> staticObjects = new ArrayList<>();

            Texture sandTriangleBlockTexture = new Texture(Gdx.files.internal("sand_triangle_block.png"));
            Pixmap pixmap = new Pixmap(sandTriangleBlockTexture.getWidth(), sandTriangleBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            sandTriangleBlockTexture.getTextureData().prepare();
            Pixmap src = sandTriangleBlockTexture.getTextureData().consumePixmap();
            pixmap.drawPixmap(src, 0, 0);
            src.dispose();
            TriangleReflector reflector = LevelHelper.getTriangleReflector(
                    12, 4, 90, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            RectangleReflector rectangleReflector = LevelHelper.getRectangleReflector(
                    8, 9, "sand_block.png");
            rectangleReflector.createBody(world);
            staticObjects.add(rectangleReflector);

            reflector = LevelHelper.getTriangleReflector(
                    12, 16, 180, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            TrainTarget target = LevelHelper.getTarget(
                    width() / 24f, width() / 4f, "target.png");
            target.setPosition(0, height() - LevelHelper.getBlockSize().y * 4f - target.getHeight());
            target.createBody(world);
            staticObjects.add(target);

            keeper.putStaticObjects(staticObjects);
            keeper.putContactListener(getContactListener(keeper));
        }
    }

    public static class Level_desert_open3 implements Level {
        @Override
        public void loadTo(Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            keeper.setScoreTableParams(Color.GOLDENROD, Gdx.graphics.getHeight());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("desert_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);

            List<StaticObject> staticObjects = new ArrayList<>();

            Texture sandTriangleBlockTexture = new Texture(Gdx.files.internal("sand_triangle_block.png"));
            Pixmap pixmap = new Pixmap(sandTriangleBlockTexture.getWidth(), sandTriangleBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            sandTriangleBlockTexture.getTextureData().prepare();
            Pixmap src = sandTriangleBlockTexture.getTextureData().consumePixmap();
            pixmap.drawPixmap(src, 0, 0);
            src.dispose();
            TriangleReflector reflector = LevelHelper.getTriangleReflector(
                    2, 9, 270, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            reflector = LevelHelper.getTriangleReflector(
                    9, 6, 90, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            reflector = LevelHelper.getTriangleReflector(
                    2, 12, 0, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            RectangleReflector rectangleReflector = LevelHelper.getRectangleReflector(
                    3, 15, "sand_block.png");
            rectangleReflector.createBody(world);
            staticObjects.add(rectangleReflector);

            reflector = LevelHelper.getTriangleReflector(
                    8, 15, 0, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            float blockWidth = width() / 12f;
            float blockHeight = (float) height() / (int) (height() / blockWidth);

            Texture texture = new Texture(Gdx.files.internal("target.png"));
            TrainTarget target = new TrainTarget(width() / 24f, width() / 4f, texture);
            target.setPosition(width() - target.getWidth(), blockHeight * 4);
            target.createBody(world);
            staticObjects.add(target);

            keeper.putStaticObjects(staticObjects);
            keeper.putContactListener(getContactListener(keeper));
        }
    }

    public static class Level_desert_open4 implements Level {
        @Override
        public void loadTo(Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            keeper.setScoreTableParams(Color.GOLDENROD, Gdx.graphics.getHeight());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("desert_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);

            List<StaticObject> staticObjects = new ArrayList<StaticObject>();


            Texture sandTriangleBlockTexture = new Texture(Gdx.files.internal("sand_triangle_block.png"));
            Pixmap pixmap = new Pixmap(sandTriangleBlockTexture.getWidth(), sandTriangleBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            sandTriangleBlockTexture.getTextureData().prepare();
            Pixmap src = sandTriangleBlockTexture.getTextureData().consumePixmap();
            pixmap.drawPixmap(src, 0, 0);
            src.dispose();
            TriangleReflector reflector = LevelHelper.getTriangleReflector(
                    2, 9, 270, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            reflector = LevelHelper.getTriangleReflector(
                    9, 6, 90, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            reflector = LevelHelper.getTriangleReflector(
                    2, 12, 0, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            RectangleReflector rectangleReflector = LevelHelper.getRectangleReflector(
                    3, 15, "sand_block.png");
            rectangleReflector.createBody(world);
            staticObjects.add(rectangleReflector);

            reflector = LevelHelper.getTriangleReflector(
                    8, 15, 0, new Texture(pixmap));
            reflector.createBody(world);
            staticObjects.add(reflector);

            float blockWidth = width() / 12f;
            float blockHeight = (float) height() / (int) (height() / blockWidth);

            Texture texture = new Texture(Gdx.files.internal("target.png"));
            TrainTarget target = new TrainTarget(width() / 24f, width() / 4f, texture);
            target.setPosition(width() - target.getWidth(), blockHeight * 12);
            target.createBody(world);
            staticObjects.add(target);

            keeper.putStaticObjects(staticObjects);
            keeper.putContactListener(getContactListener(keeper));
        }
    }

    public static class Level_desert_open5 implements Level {
        @Override
        public void loadTo(final Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("desert_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);

            List<StaticObject> staticObjects = new ArrayList<StaticObject>();

            float blockWidth = width() / 12f;
            float blockHeight = (float) height() / (int) (height() / blockWidth);

            Texture sandBlockTexture = new Texture(Gdx.files.internal("sand_block.png"));
            Pixmap pixmap = new Pixmap(sandBlockTexture.getWidth() * 4, sandBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            sandBlockTexture.getTextureData().prepare();
            Pixmap src = sandBlockTexture.getTextureData().consumePixmap();
            for (int i = 0; i < 4; i++) {
                pixmap.drawPixmap(src, sandBlockTexture.getWidth() * i, 0);
            }

            pixmap = new Pixmap((int) (sandBlockTexture.getWidth() * 12f), sandBlockTexture.getHeight() * (int) (height() / blockWidth), Pixmap.Format.RGBA4444);
            for (int i = 0; i < 12; i++) {
                pixmap.drawPixmap(src, sandBlockTexture.getWidth() * i, 0);
            }
            for (int i = 0; i < 12; i++) {
                pixmap.drawPixmap(src, sandBlockTexture.getWidth() * i, pixmap.getHeight() - sandBlockTexture.getHeight());
            }
            for (int i = 1; i < height() / blockHeight - 1; i++) {
                pixmap.drawPixmap(src, 0, sandBlockTexture.getHeight() * i);
            }
            for (int i = 1; i < height() / blockHeight - 1; i++) {
                pixmap.drawPixmap(src, pixmap.getWidth() - sandBlockTexture.getWidth(), sandBlockTexture.getHeight() * i);
            }
            BoxReflector boxReflector = new BoxReflector(pixmap, blockWidth, blockHeight);
            boxReflector.setSize(width(), height());
            boxReflector.createBody(world);
            staticObjects.add(boxReflector);

            pixmap = new Pixmap(sandBlockTexture.getWidth() * 4, sandBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            for (int i = 0; i < 4; i++) {
                pixmap.drawPixmap(src, sandBlockTexture.getWidth() * i, 0);
            }
            RectangleReflector reflector = new RectangleReflector(new Texture(pixmap), oneMeter);
            reflector.setSize(blockWidth * 4, blockHeight);
            reflector.setPosition(width() / 2f, blockHeight * 7);
            reflector.createBody(world);
            staticObjects.add(reflector);

            pixmap = new Pixmap(sandBlockTexture.getWidth() * 7, sandBlockTexture.getHeight(), Pixmap.Format.RGBA4444);
            for (int i = 0; i < 7; i++) {
                pixmap.drawPixmap(src, sandBlockTexture.getWidth() * i, 0);
            }
            reflector = new RectangleReflector(new Texture(pixmap), oneMeter);
            reflector.setSize(blockWidth * 7, blockHeight);
            reflector.setPosition(width() / 4f, blockHeight * 12);
            reflector.createBody(world);
            staticObjects.add(reflector);

            Texture texture = new Texture(Gdx.files.internal("target.png"));
            TrainTarget target = new TrainTarget(width() / 24f, width() / 4f, texture);
            target.setPosition(width() - blockWidth * 5f / 2 - target.getWidth() / 2f, height() - blockHeight - target.getHeight());
            target.createBody(world);
            staticObjects.add(target);

            keeper.putStaticObjects(staticObjects);

            keeper.putContactListener(getContactListener(keeper));
        }
    }

    public static class Level_celt_open1 implements Level {
        @Override
        public void loadTo(Keeper keeper, World world) {
            keeper.addVisualManager(LevelHelper.getClouds());
            keeper.setScoreTableParams(Color.LIME, Gdx.graphics.getHeight());
            List<Sprite> backList = new ArrayList<>();
            Sprite sprite = new Sprite(new Texture(PixmapExtensions.getLevelBack("celt_back.png")));
            sprite.setSize(width(), Gdx.graphics.getHeight());
            backList.add(sprite);

            sprite = new Sprite(new Texture(Gdx.files.internal("sun.png")));
            sprite.flip(true, false);
            sprite.setSize(width(), width() * sprite.getTexture().getHeight() / sprite.getTexture().getWidth());
            sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
            backList.add(sprite);
            keeper.putBackground(backList);
            keeper.putContactListener(getContactListener(keeper));
        }
    }

    private static ContactListener getContactListener(final Keeper keeper) {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() != null &&
                        contact.getFixtureB().getBody().getUserData() != null) {
                    if (contact.getFixtureB().getBody().getUserData().toString().substring(0, 6).equals("bullet") ||
                            contact.getFixtureA().getBody().getUserData().toString().substring(0, 6).equals("bullet")) {
                        if (contact.getFixtureA().getBody().getUserData().equals("target") ||
                                contact.getFixtureB().getBody().getUserData().equals("target")) {
                            Body id;
                            if (contact.getFixtureB().getBody().getUserData().toString().substring(0, 6).equals("bullet")) {
                                id = contact.getFixtureB().getBody();
                            } else {
                                id = contact.getFixtureA().getBody();
                            }
                            keeper.playSound("bullet_to_target");
                            keeper.putScore(20);
                            System.out.println(true);
                            keeper.resetBullet(id.getUserData().toString());
                        } else if (contact.getFixtureA().getBody().getUserData().equals("reflector") ||
                                contact.getFixtureB().getBody().getUserData().equals("reflector")) {
                            Body body;
                            if (contact.getFixtureB().getBody().getUserData().toString().substring(0, 6).equals("bullet")) {
                                body = contact.getFixtureB().getBody();
                            } else {
                                body = contact.getFixtureA().getBody();
                            }
                            if (body.getLinearVelocity().len() > 1.6f) {
                                keeper.playSound("rikoshet" + (Math.round(Math.random() * 3 + 1)));
                                keeper.showRikoshetSparks(contact.getWorldManifold().getPoints()[0]);
                            } else {
                                keeper.playSound("hit");
                            }
                            keeper.putScore(10);
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
    }*/
}
