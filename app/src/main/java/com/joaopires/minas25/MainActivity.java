package com.joaopires.minas25;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    protected TextView t;
    protected EditText w;
    protected int[] id_botoes;
    protected Button[] bArray;
    protected int numDeMinas = 10; //Variável que contém i número de minas que vão ser colocadas no jogo.
    protected int[] posicoesMinas;
    protected Button recomecar;

    public void carregarBotao(View viewRecebida){

        int a = viewRecebida.getId(); //a será igual ao ID da view recebida, o botão carregado.
        int i;
        //Precorrer o vetor de IDs de botões à procura do ID do botão que foi carregado, desta forma, sabendo qual o índice do botão carregado, e visto que
        //quer o vetor id_botoes, o vetor bArray e o vetor posicoesMinas têm o mesmo tamanho, é possivel verificar se o botão tem uma mina ou não.
        //Por exemplo, se for encontrado o ID do botão na posição 5 do vetor de IDs de seguida é verificada a posição 5 do vetor de minas,
        //se essa posição conter um 1 o botão tem uma mina, se tiver um 0 não.
        for(i = 0; i < id_botoes.length; i++){ //procurar o ID do botão no vetor de IDs

            if(id_botoes[i] == a){ //Quando o ID for encontrado
                break; //sair do ciclo for
            }
        }

        if(posicoesMinas[i] == 1){ //Verificar se o botão tem uma mina
            bArray[i].setText("M"); //Se existir uma mina é colocada a letra M no botão
        }else
            bArray[i].setText("0"); //Se não existir uma mina é colocado um 0

    }


    public void colocarMinas(){

        int[] aux = new int[56]; //Array auxiliar
        posicoesMinas = new int[56]; //Array que irá indicar se um botão tem uma mina ou não. Por exempo, se o array posicoesMina tem um 1 na posição 4 o
        //botão da posição 4 do array bArray terá uma mina.
        for(int i = 0; i < aux.length; i++){ //Preencher o vetor auxiliar com os números de 0 a 55 e iniciar o vertor de posições de minas a zero (sem nunhumas minas).

            aux[i] = i;
            posicoesMinas[i] = 0;
        }

        int auxTam = aux.length; //auxTam é igual ao comprimento do vetor aux (56)
        Random rand = new Random(); //Criação do objeto rand do tipo Random

        //Retirar um número à sorte do vetor auxiliar que servirá para indicar a posição (índice) do vetor de posições das minas em que haverá uma mina
        for (int i = 0; i < numDeMinas; i++) {
            //int aleatorio = (int)((56 - 0 + 1) * rand() + 0);
            int aleatorio = rand.nextInt(auxTam - 1); //Número aleatório entre o valor do tamanho do vetor aux menos 1 (55), para que não estejamos a ler uma posição fora do vetor
            posicoesMinas[aux[aleatorio]] = 1; //É posto um 1 na posição com o valor que foi retirado do vetor auxiliar do vetor posicoesMinas
            aux[aleatorio] = aux[auxTam - 1]; //Para que o mesmo valor não saia duas vezes é colocado o valor da última posição do vetor aux na posição da qual foi retirado o valor atual
            auxTam--; //Depois de fazer a troca em cima é decrementado um valor à variável auxTam para que o mesmo valor não tenha o dobro da probabilidade de sair.
            //Ou seja, visto que o último valor foi colocado na posição atual, de forma a remover a última posição do calculo do número aleatório é decrementado um valor à variável para que o póximo número aleatório apenas esteja entre 0 e o penultimo valor
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = (TextView)findViewById(R.id.t);
        w = (EditText)findViewById(R.id.w);

        id_botoes = new int[] {R.id.b01, R.id.b02, R.id.b03, R.id.b04, R.id.b05, R.id.b06, R.id.b07, //Array de IDs dos botões
                R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16, R.id.b17,
                R.id.b21, R.id.b22, R.id.b23, R.id.b24, R.id.b25, R.id.b26, R.id.b27,
                R.id.b31, R.id.b32, R.id.b33, R.id.b34, R.id.b35, R.id.b36, R.id.b37,
                R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45, R.id.b46, R.id.b47,
                R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55, R.id.b56, R.id.b57,
                R.id.b61, R.id.b62, R.id.b63, R.id.b64, R.id.b65, R.id.b66, R.id.b67,
                R.id.b71, R.id.b72, R.id.b73, R.id.b74, R.id.b75, R.id.b76, R.id.b77};

        bArray = new Button[56]; //Array que irá conter todos os botões

        for(int i = 0; i < id_botoes.length; i++){

            bArray[i] = (Button)findViewById(id_botoes[i]); //Criar os botões com os índices do array

            bArray[i].setOnClickListener(new View.OnClickListener() { //Criar um listner para cada botão
                @Override
                public void onClick(View v) {

                    carregarBotao(v); //Função responsável por fazer o handle dos cliques dos vários botões
                }
            });
        }

        colocarMinas(); //Colocar as minas nas suas posiçoes pela primeira vez. Inicialmente o número de minas (numDeMinas) é igual a 10

        //RECOMEÇAR//
        recomecar = (Button)findViewById(R.id.botaoRecomecar); //O botão recomeçar irá limpar o texto de todos os botões e criar o número de minas que o
                                                               //utilizador tenha introduzido no EditText
        recomecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < bArray.length; i++){ //Limpar o texto de todos os botões.

                    bArray[i].setText("");
                }

                if(w.getText().toString().equals("")){ //No caso de o utilizador carregar no botão recomeçar e o EditText não tiver nenhum texto
                    w.setText("10"); //É posto o número 10 no EditText para que não ocorra um crash por à frente ser convertida uma string vazia para int
                }

                numDeMinas = Integer.parseInt(w.getText().toString()); //Variável que indica qual o número de minas a colocar no campo

                if(numDeMinas > bArray.length - 1 || numDeMinas <= 0){ //Caso o utilizador não introduza um número que esteja entre 1 e 55, ou seja, menor ou igual a 0 ou maior ou igual ao número de botões.
                    numDeMinas = 10; //O número de minas é posto a 10
                    w.setText(Integer.toString(numDeMinas));
                }

                colocarMinas(); //Colocar de novo as minas
            }
        });

    }
}