//package com.saksham.pottercraftmod.common.particles.data;
//
//import com.mojang.brigadier.StringReader;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.particles.IParticleData;
//import net.minecraft.particles.ParticleType;
//
//public class EntityParticleData implements IParticleData {
//    private ParticleType<EntityParticleData> type;
//    public ParticleColor color;
//
//    public static final Codec<EntityParticleData> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(Codec.FLOAT.fieldOf("r").forGetter(d -> 255f),
//                    Codec.FLOAT.fieldOf("g").forGetter(d -> 255f),
//                    Codec.FLOAT.fieldOf("b").forGetter(d -> 255f))
//            .apply(instance, EntityParticleData::new)
//    );
//
//    public static final IParticleData.IDeserializer<EntityParticleData> DESERIALIZER = new IParticleData.IDeserializer<EntityParticleData>(){
//
//        @Override
//        public EntityParticleData deserialize(ParticleType<EntityParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
//            reader.expect(' ');
//            return new EntityParticleData(particleTypeIn, )
//        }
//
//        @Override
//        public EntityParticleData read(ParticleType<EntityParticleData> particleTypeIn, PacketBuffer buffer) {
//            return null;
//        }
//    };
//
//    @Override
//    public ParticleType<?> getType() {
//        return null;
//    }
//
//    @Override
//    public void write(PacketBuffer buffer) {
//
//    }
//
//    @Override
//    public String getParameters() {
//        return null;
//    }
//}
