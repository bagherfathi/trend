package org.elasticsearch.index.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
// import java.util.stream.Stream;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
// import static java.util.stream.Collectors.toList;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public final class FarsiNlpTokenizer extends Tokenizer {
    private final static Logger LOGGER = Logger.getGlobal();

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);
    private final PositionIncrementAttribute posIncAtt = addAttribute(PositionIncrementAttribute.class);

    private Boolean doneGetInputString = false;
    //private String usingRawString = "";
    //private int tokenIndex = 0;
    //private int sentenceIndex = 0;
    //private Boolean morphemeCheckDoneOnTokens = false;
    //List<Sentence> sentences;
    //ArrayList<MyToken> tokens;


    public FarsiNlpTokenizer(IndexSettings indexSettings, Environment environment, Settings settings) {
        
        super();
    }

    public FarsiNlpTokenizer() {

    }

    @Override
    public boolean incrementToken() throws IOException {
        return false;
    }



  
/*
    private boolean isLastSentence() {
        if (sentences.size() == sentenceIndex + 1) {
            return true;
        }
        return false;
    }

    private boolean isLastToken() {
        if (tokens.size() <= tokenIndex) {
            return true;
        }
        return false;
    }
 */
//    private ArrayList<MyToken> getWords(List<Sentence> sentences) {
//        ArrayList<MyToken> tokens = new ArrayList<MyToken>();
//
//        for( Sentence sentence : sentences) {
//            for( Token t : sentence.getTokensList() ) {
//                int iMorpheme = 0;
//                for( Morpheme morpheme : t.getMorphemesList()) {
//                    String text = morpheme.getText().getContent();
//                    String tag = morpheme.getTag().name();
//                    //System.out.println(text + " " + tag);
//                    String tokenName = caller.isEsToken(tag);
//                    if (!tokenName.isEmpty()) {
//                        MyToken temp2 = new MyToken();
//                        temp2.text = text;
//                        temp2.beginOffset = morpheme.getText().getBeginOffset();
//                        temp2.length = temp2.text.length();
//                        temp2.type = tokenName;
//                        temp2.positionInc = 1;
//                        //temp2.isLast = false;
//                        //temp2.isEmpty = false;
//                        tokens.add(temp2);
//
//                        // TODO : VV나 VA인경우 어절 전체를 등록한다.
//                        if( iMorpheme == 0 && (tokenName == "VV" ||  tokenName == "VA") &&
//                            t.getText().getContent() != text ) {
//                            MyToken token = new MyToken();
//                            token.text = t.getText().getContent();
//                            token.beginOffset = t.getText().getBeginOffset();
//                            token.length = token.text.length();
//                            token.type = tokenName;
//                            token.positionInc = 0;
//                            tokens.add(token);
//                        }
//                    }
//                    iMorpheme++;
//                }
//            }
//        }
//        return tokens;
//    }
///*
//    private void makeTokens() {
//        tokens = new ArrayList<MyToken>();
//        //System.out.println(sentences);
//
//        for (int i = 0; i < sentences.get(sentenceIndex).getTokensCount(); i++) {
//
//            for (int j = 0; j < sentences.get(sentenceIndex).getTokens(i).getMorphemesCount(); j++) {
//                Morpheme morpheme = sentences.get(sentenceIndex).getTokens(i).getMorphemes(j);
//                String text = morpheme.getText().getContent();
//                String tag = morpheme.getTag().name();
//                //System.out.println(text + " " + tag);
//                String tokenName = caller.isEsToken(tag);
//                if (!tokenName.isEmpty()) {
//                    MyToken temp2 = new MyToken();
//                    temp2.text = morpheme.getText().getContent();
//                    temp2.beginOffset = morpheme.getText().getBeginOffset();
//                    temp2.length = temp2.text.length();
//                    temp2.type = tokenName;
//                    temp2.isLast = false;
//                    temp2.isEmpty = false;
//                    tokens.add(temp2);
//                }
//            }
//        }
//    }
//
//    private MyToken nextToken() {
//        if (isLastToken()) {
//            if (isLastSentence()) {
//                MyToken temp = new MyToken();
//                temp.isLast = true;
//                return temp;
//            }
//            tokenIndex = 0;
//            sentenceIndex++;
//            makeTokens();
//        }
//        if (!tokens.isEmpty() && tokens.size() > tokenIndex) {
//            return tokens.get(tokenIndex++);
//        } else {
//            MyToken temp = new MyToken();
//            temp.isEmpty = true;
//            return temp;
//        }
//    }
// */
//
//    @Override
//    public boolean incrementToken() throws IOException {
//        clearAttributes();
//        if (!doneGetInputString) {
//            // sentences = null;
//            AnalyzeSyntaxResponse response = getInputString();
//            if (response == null) return false;
//
//            /*
//            try {
//                String jsonString = JsonFormat.printer().includingDefaultValueFields().print(response);
//                LOGGER.info(jsonString);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//             */
//            List<Sentence> sentences = response.getSentencesList();
//            if (sentences == null || sentences.size() == 0) return false;
//            List<MyToken> tokens = getWords(sentences);
//            this.itMyToken = tokens.iterator();
//
//        }
//        if( itMyToken != null && itMyToken.hasNext() ) {
//            MyToken item = itMyToken.next();
//            termAtt.append(item.text);
//            typeAtt.setType(item.type);
//            offsetAtt.setOffset(item.beginOffset, item.beginOffset + item.length);
//            posIncAtt.setPositionIncrement(item.positionInc);
//            return true;
//        }
//        return false;
//    }
//
///*
//    @Override
//    public boolean incrementTokenOld() throws IOException {
//        // TODO proxy로 grpc 보내고 결과를 tokenizing 해서 token filter로 보내기
//        clearAttributes();
//        if (!doneGetInputString) {
//            // sentences = null;
//            AnalyzeSyntaxResponse response = getInputString();
//            if (temp == null) return false;
//
//            List<Sentence> tempList = temp.getSentencesList();
//            // sentences = tempList;
//            if (sentences == null || sentences.size() == 0) return false;
//            makeTokens();
//        }
//        MyToken item = nextToken();
//        if (item.isLast) {
//            return false;
//        } else {
//            while(item.isEmpty) {
//                item = nextToken();
//            }
//            if (item.isLast) {
//                return false;
//            }
//            termAtt.append(item.text);
//            typeAtt.setType(item.type);
//            offsetAtt.setOffset(item.beginOffset, item.beginOffset + item.length);
//        }
//        return true;
//    }
//*/
//    @Override
//    public void close() throws IOException {
//        super.close();
//        doneGetInputString = false;
//        itMyToken = null;
//        // tokenIndex = 0;
//        // sentenceIndex = 0;
//    }
//
//    @Override
//    public void reset() throws IOException {
//        super.reset();
//        doneGetInputString = false;
//        itMyToken = null;
//        // tokenIndex = 0;
//        // sentenceIndex = 0;
//    }
//
//    @Override
//    public void end() throws IOException {
//        super.end();
//        doneGetInputString = false;
//        itMyToken = null;
//        // tokenIndex = 0;
//        // sentenceIndex = 0;
//    }
}
