///client_create()

RESULT = -1;

network_set_config(network_config_connect_timeout, 5000);

SOCKET = network_create_socket(network_socket_tcp);
ISCONNECTED = network_connect_raw(SOCKET, "127.0.0.1", 6790);

PEOPLE = ds_map_create();

PLACE_DIV_X = 672;
PLACE_DIV_Y = 672;
PLACE_ITEM_R = 100;

ITEM_X = 0;
ITEM_Y = 0;
ITEM_WIDTH = 0;
ITEM_HEIGHT = 0;
ITEMPOSITIONS[0, 0] = 0;
PERSON_X = 0;
PERSON_Y = 0;
PERSON_WIDTH = 0;
PERSON_HEIGHT = 0;

MAXITEMS = -1;

v_pos_id = 0;


draw_set_color(c_black);
draw_set_font(font_main);

if (ISCONNECTED >= 0){
    room_goto(room_visualization);
}
