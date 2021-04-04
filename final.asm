section .bss

section .data
var0  db 'Ola ',0XA, 0XD
len_var0 equ $- var0

section .text

global _start

_start:

mov rcx, var0
mov rdx, len_var0
mov rbx, 1
mov rax, 4
int 0x80


mov rax, 1
int 80h

