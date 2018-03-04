///_Visitor(id, items, desired_items, status)

with(instance_create(-100, -100, Visitor)){
    ID = argument0;
    items = argument1;
    desired = argument2;
    status = argument3;
    v_pos_id = Client.v_pos_id;
    ds_map_add(Client.PEOPLE, argument0, id);
    
    disp = v_pos_id * Client.PLACE_GAP_X;
    
    x = Client.PLACE_START_X + disp;
    y = Client.PLACE_START_Y;
    
    gotox = x;
    gotoy = y;
}

Client.v_pos_id++;
