/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


export function hello(text) {
  const div = document.createElement('div');
  div.textContent = `Hello ${text}`;
  document.body.appendChild(div);
}