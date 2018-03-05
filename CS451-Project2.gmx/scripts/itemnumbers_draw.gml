///itemnumbers_draw()

for(var i = 0; i < Client.MAXITEMS + 1; i++){
    draw_text_transformed(
        Client.ITEMPOSITIONS[i, 0] + (Client.ITEM_WIDTH / 2),
        Client.ITEMPOSITIONS[i, 1] + (Client.ITEM_HEIGHT / 2),
        i, 2.5, 2.5, 0);
}
