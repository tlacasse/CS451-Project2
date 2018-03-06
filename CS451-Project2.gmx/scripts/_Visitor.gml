///_Visitor(id, items, desired_items, status)

with(instance_create(-100, -100, Visitor)){
    ID = argument0;
    items = argument1;
    desired = argument2;
    status = argument3;
    pos_id = Client.v_pos_id;
    
    ds_map_add(Client.PEOPLE, argument0, id);
    
    
    disp_x = (Client.ITEM_WIDTH * 0.15) + ((pos_id % Client.PERSON_X) * Client.PERSON_WIDTH);
    disp_y = (Client.ITEM_HEIGHT * 0.1) + ((pos_id div Client.PERSON_X) * Client.PERSON_HEIGHT);
    
    x = Client.ITEMPOSITIONS[items, 0] + disp_x;
    y = Client.ITEMPOSITIONS[items, 1] + disp_y;
    
    gotox = x;
    gotoy = y;
}

Client.v_pos_id++;
