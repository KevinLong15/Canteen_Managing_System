while(1){
	c = -1;
	next = 0;
	i = NR_TASKS;
	p = &task[NR_TASKS];
	while(--i){
		if(!*--p)
			continue;
		if((*p)->state == TASK_RUNNING && (*P)->counter > c)
			c = (*P)->counter, next = i;
	}
	if (c) break;
	for(p = &LAST_TASK ; p > &FIRST_TASK ; --p)
		if(*p)
			(*p)->counter = ((*p)->counter >> 1) + 
							(*p)->priority;
							
							
while(1){
	c = -1;
	next = 0;
	i = NR_TASKS;
	p = &task[NR_TASKS];
	while(--i){
		if(!*--p)
			continue;
		if((*p)->state == TASK_RUNNING){
			next = i;
			c=1;
			
			break;
		}
	}
	if (c) break;