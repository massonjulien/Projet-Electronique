#include "C:\Users\JULIEN\Desktop\cours\ElectroProj\zlkadmlkaz\elecTrue\elecTrue.h"

int32 dist(void);
int32 limite = 0;
int32 distance;
void main(){
   printf("Bienvenu dans la simulation proetus\n");
   setup_timer_0(RTCC_INTERNAL|RTCC_DIV_8|RTCC_8_BIT);
   setup_timer_1(T1_INTERNAL|T1_DIV_BY_1);
   enable_interrupts(GLOBAL);
   setup_low_volt_detect(FALSE);
   enable_interrupts(INT_RDA);
   enable_interrupts(GLOBAL);
   distance = 0;
   while(true){
      distance = dist();
      if(distance < limite){
         output_high(PIN_B0);
         output_low(PIN_B1);
         printf("distance trop petite");
         printf("%lu", distance);
      } else {
         output_high(PIN_B1);
         output_low(PIN_B0);
         do{
            delay_ms(1000);
            distance = dist();
            printf("%lu\r\n",distance);
         }while(distance>limite);
      }
   }
}

int32 dist(){
   float time=0;
   int32 distance=0;
   output_high(pin_b7); 
   delay_us(10);
   output_low(pin_b7);
   while(!input(PIN_b3));
   set_timer1(0);
   while(input(PIN_b3));
   time=get_timer1();
   distance = time* 0.00344;
   return distance;
}

