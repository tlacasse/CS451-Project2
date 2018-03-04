///client_create()

RESULT = -1;

network_set_config(network_config_connect_timeout, 5000);

SOCKET = network_create_socket(network_socket_tcp);
ISCONNECTED = network_connect_raw(SOCKET, "127.0.0.1", 6790);

PEOPLE = ds_map_create();

PLACE_START_X = 50;
PLACE_START_Y = 80;
PLACE_GAP_X = 20;
PLACE_GAP_Y = 20;

maxitems = -1;
v_pos_id = 0;


draw_set_color(c_black);
draw_set_font(font_main);
